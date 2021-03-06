/*
 * MIT License
 *
 * Copyright (c) 2018 Xenon and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.Bootstrap
import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.xenon.Dimension
import com.github.xenonminecraft.xenon.GameMode
import com.github.xenonminecraft.xenon.LevelType
import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.client.login.PacketClientEncryptionResponse
import com.github.xenonminecraft.network.packet.client.login.PacketClientLoginStart
import com.github.xenonminecraft.network.packet.server.login.PacketServerEncryptionRequest
import com.github.xenonminecraft.network.packet.server.login.PacketServerLoginSuccess
import com.github.xenonminecraft.network.packet.server.login.PacketServerSetCompression
import com.github.xenonminecraft.network.packet.server.play.*
import com.github.xenonminecraft.network.util.writeString
import com.github.xenonminecraft.xenon.Position
import io.netty.buffer.Unpooled
import java.util.*

class LoginHandler(val pc: PlayerConnection) {

    val em = Xenon.instance!!.encryptionManager

    fun handle(p: Packet) {
        when(p) {
            is PacketClientLoginStart -> {
                pc.username = p.username!!
                val verifyToken = em.generateByteArray(4)
                pc.verifyToken = verifyToken
                val enc = PacketServerEncryptionRequest(em.publicKey.encoded, verifyToken)
                pc.sendPacket(enc)
            }
            is PacketClientEncryptionResponse -> {
                if(pc.verifyToken == null) {
                    TODO("Close Connection")
                    return
                }

                if(Xenon.instance!!.config.getBoolean("server.online-mode")) {
                    //Authenticate
                    pc.sharedSecret = p.getSecretKey(em.privateKey)
                    val serverHash = em.hashToString(em.getServerIdHash("", em.publicKey, pc.sharedSecret!!))
                    val gp = Xenon.instance!!.sessionManager.authenticate(pc.username!!, pc.addr.hostString, serverHash)

                    if(gp == null) {
                        TODO("Close Connection")
                        return
                    }
                    pc.gameProfile = gp
                    pc.enableEncryption()

                    val compthresh = Xenon.instance!!.config.getLong("network.compression-threshold").toInt()
                    if(compthresh > -1) {
                        pc.sendPacket(PacketServerSetCompression(compthresh))
                        pc.compressionThreshold = compthresh
                        pc.enableCompression()
                    }

                    pc.sendPacket(PacketServerLoginSuccess(UUID.fromString(gp.uuid), gp.name))

                    pc.state = PlayerConnection.State.PLAY
                    pc.sendPacket(PacketServerJoinGame(0, GameMode.SURVIVAL, Dimension.OVERWORLD,
                            Xenon.instance!!.difficulty, 0, LevelType.DEFAULT)) //Will need to read player data from disk later
                    sendBrand()

                    pc.sendPacket(PacketServerDifficulty(Xenon.instance!!.difficulty))

                    pc.sendPacket(PacketServerSpawnPosition(Position()))


                    pc.sendPacket(PacketServerPlayerAbilities(false, false, false, false, 1F, 1F))

                    Xenon.LOGGER.info("Player ${gp.name} (${gp.uuid}) has joined the game!")

                }
            }
        }
    }

    private fun sendBrand() {
        val buf = Unpooled.buffer()
        buf.writeString("Xenon ${Bootstrap.XENON_VERSION}")
        pc.sendPacket(PacketServerPluginMessage("brand", buf.array()))
    }
}
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

package com.github.xenonminecraft.network

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.network.auth.GameProfile
import com.github.xenonminecraft.network.netty.MinecraftPacketCompressor
import com.github.xenonminecraft.network.netty.MinecraftPacketDecompressor
import com.github.xenonminecraft.network.netty.PacketEncryptionDecoder
import com.github.xenonminecraft.network.netty.PacketEncryptionEncoder
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.server.play.PacketServerPlayerPositionAndLook
import com.github.xenonminecraft.xenon.ChatMode
import com.github.xenonminecraft.xenon.MainHand
import com.github.xenonminecraft.xenon.SkinDetails
import io.netty.channel.socket.SocketChannel
import java.net.InetSocketAddress
import javax.crypto.SecretKey

class PlayerConnection(val addr: InetSocketAddress, val ch: SocketChannel) {
    var state: State = State.NONE
    var protocolVersion: Int = 0

    var compressionThreshold: Int = -1
    private var compress = false

    private var encrypted = false
    var sharedSecret: SecretKey? = null
    var verifyToken: ByteArray? = null
    var username: String? = null
    var gameProfile: GameProfile? = null

    var loginCompleted = false

    var locale = ""
    var mainHand: MainHand? = null
    var skinDetails: SkinDetails? = null
    var chatMode: ChatMode? = null
    var chatColors = true
    var viewDistance: Int? = null

    var x: Double = 0.0
    var y: Double = 0.0
    var z: Double = 0.0
    var yaw: Float = 0F
    var pitch: Float = 0F

    private var unconfirmedTeleportIds = mutableListOf<Int>()

    fun teleport(x: Double, y: Double, z: Double, pitch: Float, yaw: Float) {
        val id = Xenon.RANDOM.nextInt(100) + 1
        unconfirmedTeleportIds.add(id)
        this.x = x
        this.y = y
        this.z = z
        this.yaw = yaw
        this.pitch = pitch
        sendPacket(PacketServerPlayerPositionAndLook(x, y, z, yaw, pitch, 0, id))
    }

    fun confirmTeleport(id: Int): Boolean {
        return unconfirmedTeleportIds.remove(id)
    }

    fun sendPacket(p: Packet) {
        ch.writeAndFlush(p)
    }

    fun dumpPipeline() {
        ch.pipeline().forEach {
            println(it.key)
        }
    }

    fun enableEncryption() {
        if (encrypted)
            return
        ch.pipeline().addBefore("splitter", "decrypt",
                PacketEncryptionDecoder(Xenon.instance!!.encryptionManager.createNetCipher(2, sharedSecret!!)))
        ch.pipeline().addBefore("prepender", "encrypt",
                PacketEncryptionEncoder(Xenon.instance!!.encryptionManager.createNetCipher(1, sharedSecret!!)))
        encrypted = true
    }

    fun enableCompression() {
        if (compress)
            return
        ch.pipeline().addAfter("splitter", "decompress", MinecraftPacketDecompressor(this))
        ch.pipeline().addAfter("prepender", "compress", MinecraftPacketCompressor(this))
        compress = true
    }

    enum class State(val id: Int) {
        NONE(0),
        LOGIN(2),
        PLAY(0),
        STATUS(1);

        companion object {
            fun getById(id: Int): State = State.values().first { it.id == id }
        }
    }
}
package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.client.login.PacketClientEncryptionResponse
import com.github.xenonminecraft.network.packet.client.login.PacketClientLoginStart
import com.github.xenonminecraft.network.packet.server.login.PacketServerEncryptionRequest
import com.github.xenonminecraft.network.packet.server.login.PacketServerLoginSuccess
import com.github.xenonminecraft.network.packet.server.login.PacketServerSetCompression
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

                    val compthresh = Xenon.instance!!.config.getLong("network.compression-threshold").toInt()
                    if(compthresh > -1) {
                        pc.sendPacket(PacketServerSetCompression(compthresh))
                        pc.compressionThreshold = compthresh
                        pc.enableCompression()
                    }

                    pc.sendPacket(PacketServerLoginSuccess(UUID.fromString(gp.uuid), gp.name))
                    pc.state = PlayerConnection.State.PLAY

                    Xenon.LOGGER.info("Player ${gp.name} (${gp.uuid}) has joined the game!")
                }
            }
        }
    }
}
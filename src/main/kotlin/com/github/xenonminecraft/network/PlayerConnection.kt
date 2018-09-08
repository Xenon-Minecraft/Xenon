package com.github.xenonminecraft.network

import com.github.xenonminecraft.network.auth.GameProfile
import com.github.xenonminecraft.network.packet.Packet
import io.netty.channel.socket.SocketChannel
import java.net.InetSocketAddress
import javax.crypto.SecretKey

class PlayerConnection(val addr: InetSocketAddress, val ch: SocketChannel) {
    var state: State = State.NONE
    var compressionThreshold: Int = -1
    var protocolVersion: Int = 0

    var sharedSecret: SecretKey? = null
    var verifyToken: ByteArray? = null
    var username: String? = null
    var gameProfile: GameProfile? = null

    fun sendPacket(p: Packet) {
        ch.writeAndFlush(p)
    }

    enum class State(val id: Int) {
        NONE(0),
        LOGIN(2),
        PLAY(0),
        STATUS(1);

        companion object {
            fun getById(id: Int): State = State.values().first { it.id == id}
        }
    }
}
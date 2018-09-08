package com.github.xenonminecraft.network

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.network.auth.GameProfile
import com.github.xenonminecraft.network.netty.PacketEncryptingDecoder
import com.github.xenonminecraft.network.netty.PacketEncryptingEncoder
import com.github.xenonminecraft.network.packet.Packet
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.SocketChannel
import java.net.InetSocketAddress
import javax.crypto.SecretKey

class PlayerConnection(val addr: InetSocketAddress, val ch: SocketChannel, val channelContext: ChannelHandlerContext) {
    private var state: State = State.NONE
    var compressionThreshold: Int = -1
    private var protocolVersion: Int = 0

    private var encrypted = false
    private var sharedSecret: SecretKey? = null
    private var verifyToken: ByteArray? = null
    var username: String? = null
    var gameProfile: GameProfile? = null

    fun sendPacket(p: Packet) {
        ch.writeAndFlush(p)
    }

    fun enableEncryption() {
        if (encrypted)
            return
        channelContext.channel().pipeline().addBefore("splitter", "decrypt",
                PacketEncryptingDecoder(Xenon.instance!!.encryptionManager.createNetCipher(2, sharedSecret!!)))
        channelContext.channel().pipeline().addBefore("prepender", "encrypt",
                PacketEncryptingEncoder(Xenon.instance!!.encryptionManager.createNetCipher(1, sharedSecret!!)))
        encrypted = true
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
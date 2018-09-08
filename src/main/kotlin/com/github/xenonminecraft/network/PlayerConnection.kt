package com.github.xenonminecraft.network

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.network.auth.GameProfile
import com.github.xenonminecraft.network.netty.*
import com.github.xenonminecraft.network.packet.Packet
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.SocketChannel
import java.net.InetSocketAddress
import javax.crypto.SecretKey

class PlayerConnection(val addr: InetSocketAddress, val ch: SocketChannel, val channelContext: ChannelHandlerContext) {
    var state: State = State.NONE
    var protocolVersion: Int = 0

    var compressionThreshold: Int = -1
    private var compress = false

    private var encrypted = false
    var sharedSecret: SecretKey? = null
    var verifyToken: ByteArray? = null
    var username: String? = null
    var gameProfile: GameProfile? = null

    fun sendPacket(p: Packet) {
        ch.writeAndFlush(p)
    }

    fun enableEncryption() {
        if (encrypted)
            return
        channelContext.channel().pipeline().addBefore("splitter", "decrypt",
                PacketEncryptionDecoder(Xenon.instance!!.encryptionManager.createNetCipher(2, sharedSecret!!)))
        channelContext.channel().pipeline().addBefore("prepender", "encrypt",
                PacketEncryptionEncoder(Xenon.instance!!.encryptionManager.createNetCipher(1, sharedSecret!!)))
        encrypted = true
    }

    fun enableCompression() {
        if(compress)
            return
        channelContext.channel().pipeline().addAfter("splitter", "decompress", MinecraftPacketDecompressor(this))
        channelContext.channel().pipeline().addAfter("prepender", "compress", MinecraftPacketCompressor(this))
        compress = true
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
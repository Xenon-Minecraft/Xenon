package com.theultimatepleb.minecraft.server.network.netty

import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.chat.ChatComponent
import com.theultimatepleb.minecraft.server.mojang.auth.GameProfile
import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.network.handler.ILoginHandler
import com.theultimatepleb.minecraft.server.network.handler.IPlayHandler
import com.theultimatepleb.minecraft.server.network.handler.IStatusHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.packet.handshaking.HandshakeHandler
import com.theultimatepleb.minecraft.server.protocol.packet.login.LoginHandler
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketDisconnect
import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketEncryptionResponse
import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketLoginStart
import com.theultimatepleb.minecraft.server.protocol.packet.play.PlayHandler
import com.theultimatepleb.minecraft.server.protocol.packet.status.StatusHandler
import com.theultimatepleb.minecraft.server.protocol.packet.status.clientbound.PacketPong
import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketPing
import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketRequest
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import java.net.InetSocketAddress
import javax.crypto.SecretKey


/**
 * Created by Sebastian Agius on 13/08/2017.
 */
class ConnectionHandler(val address: InetSocketAddress,
                        private val networkManager: NetworkManager) : ChannelInboundHandlerAdapter() {
    var mode
        get() = networkManager.states[address]
        set(m) {
            networkManager.states[address] = m!!
        }

    var connectedIp = ""
    private var minecraftServer = MinecraftServer.instance!!
    var gameProfile: GameProfile? = null

    private var isEncrypted = false

    var handler: IHandler? = null

    var channelContext: ChannelHandlerContext? = null

    var sharedSecret: SecretKey? = null

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        networkManager.states.remove(address)
        channelContext = null
    }

    fun enableEncryption() {
        if (isEncrypted)
            return
        channelContext!!.channel().pipeline().addBefore("splitter", "decrypt",
                PacketEncryptingDecoder(minecraftServer.encryptionManager.createNetCipher(2, sharedSecret!!)))
        channelContext!!.channel().pipeline().addBefore("prepender", "encrypt",
                PacketEncryptingEncoder(minecraftServer.encryptionManager.createNetCipher(1, sharedSecret!!)))
    }

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        channelContext = ctx
        val packet = msg as Packet
        when (mode) {
            NetworkManager.State.NONE -> if (handler == null) handler = HandshakeHandler(this)
            NetworkManager.State.STATUS -> if (handler !is IStatusHandler) handler = StatusHandler(this, minecraftServer)
            NetworkManager.State.LOGIN -> if (handler !is ILoginHandler) handler = LoginHandler(this, minecraftServer)
            NetworkManager.State.PLAY -> if (handler !is IPlayHandler) handler = PlayHandler(this)
            null -> return
        }

        packet.processPacket(handler!!)
    }

    fun sendPacket(packet: Packet) {
        channelContext!!.writeAndFlush(packet)
    }

}
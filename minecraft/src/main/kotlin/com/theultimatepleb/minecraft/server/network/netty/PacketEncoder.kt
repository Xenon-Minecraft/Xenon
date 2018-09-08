package com.theultimatepleb.minecraft.server.network.netty

import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.net.InetSocketAddress

/**
 * Created by Sebastian Agius on 10/09/2017.
 */
class PacketEncoder(val address: InetSocketAddress, val networkManager: NetworkManager) : MessageToByteEncoder<Packet>() {
    override fun encode(ctx: ChannelHandlerContext?, packet: Packet?, out: ByteBuf?) {
        val buf = Unpooled.buffer(1024)
        val encoded = packet!!.encode()

        val id by lazy {
            when(networkManager.states[address]) {
                NetworkManager.State.STATUS -> Packet.clientboundStatus.entries
                        .filter { it.value == packet::class.java }[0].key
                NetworkManager.State.LOGIN -> Packet.clientboundLogin.entries
                        .filter { it.value == packet::class.java }[0].key
                NetworkManager.State.PLAY -> Packet.clientboundPlay.entries
                        .filter { it.value == packet::class.java }[0].key
                else -> 0
            }
        }



        BaseType.VAR_INT.write(buf, id)

        buf.writeBytes(encoded)

        out!!.writeBytes(buf)
    }
}
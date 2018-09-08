package com.theultimatepleb.minecraft.server.network.netty

import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.net.InetSocketAddress

/**
 * Created by Sebastian Agius on 10/09/2017.
 */
class PacketDecoder(val address: InetSocketAddress, val networkManager: NetworkManager) : ByteToMessageDecoder() {
    val mode
        get() = networkManager.states[address]

    override fun decode(ctx: ChannelHandlerContext?, inBuf: ByteBuf?, out: MutableList<Any>?) {
        if(inBuf!!.readableBytes() == 0)
            return

        val packetId = BaseType.VAR_INT.read(inBuf)
        val p: Class<out Packet>?
        p = when (mode) {
            NetworkManager.State.NONE -> Packet.handshake[packetId]
            NetworkManager.State.STATUS -> Packet.serverboundStatus[packetId]
            NetworkManager.State.LOGIN -> Packet.serverboundLogin[packetId]
            NetworkManager.State.PLAY -> Packet.serverboundPlay[packetId]
            else -> return
        }

        if(p == null)
            return



        val packet = p.newInstance()

        try {
            packet.decode(inBuf)
        } catch(e: Exception) {
            e.printStackTrace()
            return
        }

        if(inBuf.readableBytes() > 0) {
            //Packet still has bytes
        }

        out!!.add(packet)

    }
}
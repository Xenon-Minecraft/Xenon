package com.theultimatepleb.minecraft.server.protocol.packet.status.clientbound

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketPong(var payload: Long) : Packet {

    override fun processPacket(handler: IHandler) {
        throw UnsupportedOperationException(Packet.CANNOT_PROCESS_CLIENTBOUND)
    }

    override fun decode(byteBuf: ByteBuf): Packet {
        throw UnsupportedOperationException(Packet.CANNOT_DECODE_CLIENTBOUND)
    }

    override fun encode(): ByteBuf {
        val byteBuf = Unpooled.buffer()
        BaseType.LONG.write(byteBuf, payload)
        return byteBuf
    }
}
package com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketSetCompression(var threshold: Int = 256) : Packet{

    override fun decode(byteBuf: ByteBuf): Packet {
        throw UnsupportedOperationException(Packet.CANNOT_DECODE_CLIENTBOUND)
    }

    override fun processPacket(handler: IHandler) {
        throw UnsupportedOperationException(Packet.CANNOT_PROCESS_CLIENTBOUND)
    }

    override fun encode(): ByteBuf {
        val b = Unpooled.buffer()
        BaseType.VAR_INT.write(b, threshold)
        return b
    }
}
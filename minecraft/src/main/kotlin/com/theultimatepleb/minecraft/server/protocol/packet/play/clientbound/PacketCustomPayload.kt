package com.theultimatepleb.minecraft.server.protocol.packet.play.clientbound

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Created by Sebastian Agius(TheUltimatePleb) on 15/09/17.
 */
class PacketCustomPayload(val channel:String, val data: ByteBuf) : Packet {

    init {
        if(data.writerIndex() > 1048576) throw IllegalArgumentException("Payload cannot be larger than 1048576 bytes")
    }

    override fun decode(byteBuf: ByteBuf): Packet {
        throw UnsupportedOperationException(Packet.CANNOT_DECODE_CLIENTBOUND)
    }

    override fun encode(): ByteBuf {
        val b = Unpooled.buffer()

        BaseType.STRING.write(b, channel)
        b.writeBytes(data)

        return b
    }

    override fun processPacket(handler: IHandler) {
        throw UnsupportedOperationException(Packet.CANNOT_PROCESS_CLIENTBOUND)
    }

}
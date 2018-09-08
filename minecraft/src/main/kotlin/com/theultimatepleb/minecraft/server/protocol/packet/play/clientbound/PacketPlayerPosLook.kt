package com.theultimatepleb.minecraft.server.protocol.packet.play.clientbound

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Created by Sebastian Agius(TheUltimatePleb) on 15/09/17.
 */
class PacketPlayerPosLook(val x: Double, val y: Double, val z: Double,
                          val yaw: Float, val pitch: Float) : Packet {
    //TODO: Handling Flags

    override fun decode(byteBuf: ByteBuf): Packet {
        throw UnsupportedOperationException(Packet.CANNOT_DECODE_CLIENTBOUND)
    }

    override fun encode(): ByteBuf {
        val b = Unpooled.buffer()

        b.writeDouble(x)
        b.writeDouble(y)
        b.writeDouble(z)
        b.writeFloat(yaw)
        b.writeFloat(pitch)
        b.writeByte(0x01 and 0x02 and 0x03)
        BaseType.VAR_INT.write(b, 0)

        return b
    }

    override fun processPacket(handler: IHandler) {
        throw UnsupportedOperationException(Packet.CANNOT_PROCESS_CLIENTBOUND)
    }
}
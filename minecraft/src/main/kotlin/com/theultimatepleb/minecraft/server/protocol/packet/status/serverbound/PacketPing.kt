package com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.network.handler.IStatusHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketPing(var payload: Long = 0) : Packet {
    override fun processPacket(handler: IHandler) {
        (handler as IStatusHandler).handlePing(this)
    }

    override val id = 0x01

    override fun decode(byteBuf: ByteBuf): Packet {
        payload = BaseType.LONG.read(byteBuf)
        return this
    }

    override fun encode(): ByteBuf {
        throw UnsupportedOperationException(Packet.CANNOT_ENCODE_SERVERBOUND)
    }
}
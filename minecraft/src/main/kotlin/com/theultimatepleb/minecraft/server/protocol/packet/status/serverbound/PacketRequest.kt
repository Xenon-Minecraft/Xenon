package com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.network.handler.IStatusHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import io.netty.buffer.ByteBuf

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketRequest : Packet {
    override fun processPacket(handler: IHandler) {
        (handler as IStatusHandler).handleRequest(this)
    }

    override val id = 0x00

    override fun decode(byteBuf: ByteBuf): Packet = this

    override fun encode(): ByteBuf {
        throw UnsupportedOperationException(Packet.CANNOT_ENCODE_SERVERBOUND)
    }
}
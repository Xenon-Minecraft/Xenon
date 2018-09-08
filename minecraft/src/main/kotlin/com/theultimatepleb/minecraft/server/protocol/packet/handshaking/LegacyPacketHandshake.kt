package com.theultimatepleb.minecraft.server.protocol.packet.handshaking

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import io.netty.buffer.ByteBuf

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class LegacyPacketHandshake : Packet {
    override fun processPacket(handler: IHandler) {
        TODO("not implemented")
    }

    override fun decode(byteBuf: ByteBuf): LegacyPacketHandshake {
        TODO("not implemented")
    }

    override fun encode(): ByteBuf {
        TODO("not implemented")
    }
}
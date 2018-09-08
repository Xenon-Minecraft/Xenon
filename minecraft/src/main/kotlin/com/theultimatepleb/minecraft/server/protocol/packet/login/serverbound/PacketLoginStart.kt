package com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.network.handler.ILoginHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketLoginStart(var name: String? = null) : Packet {

    override fun processPacket(handler: IHandler) {
        (handler as ILoginHandler).handleLoginStart(this)
    }

    override fun decode(byteBuf: ByteBuf): Packet {
        name = BaseType.STRING.read(byteBuf)
        return this
    }

    override fun encode(): ByteBuf {
        throw UnsupportedOperationException(Packet.CANNOT_ENCODE_SERVERBOUND)
    }
}
package com.theultimatepleb.minecraft.server.protocol.packet.handshaking

import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.network.handler.IHandshakeHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
data class PacketHandshake(var protocolNumber: Int = 0,
                           var address: String = "",
                           var port: Int = 0,
                           var state: NextState = NextState.NONE) : Packet {
    override fun processPacket(handler: IHandler) {
        (handler as IHandshakeHandler).handleHandshake(this)
    }

    override fun decode(byteBuf: ByteBuf): PacketHandshake {
        protocolNumber = BaseType.VAR_INT.read(byteBuf)
        address = BaseType.STRING.read(byteBuf)
        port = byteBuf.readUnsignedShort()
        state = NextState.byId(BaseType.VAR_INT.read(byteBuf))
        return this
    }

    override fun encode(): ByteBuf {
        throw UnsupportedOperationException(Packet.CANNOT_ENCODE_SERVERBOUND)
    }

    enum class NextState(val id: Int) {
        NONE(0), STATUS(1), LOGIN(2);

        companion object {
            fun byId(id: Int): NextState {
                var state: NextState? = null
                NextState.values().forEach { if (it.id == id) state = it }
                return if (state == null) NONE else state!!
            }
        }
    }

}
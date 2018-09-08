package com.theultimatepleb.minecraft.server.protocol.packet.play.clientbound

import com.theultimatepleb.minecraft.crystal.GameMode
import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Created by Sebastian Agius on 15/09/2017.
 */
class PacketJoinGame(val entityId: Int, val gameMode: GameMode, val dimension: Int,
                     val difficulty: Int, val levelType: String = "flat", val reducedDebug: Boolean = false) : Packet {

    override fun decode(byteBuf: ByteBuf): Packet {
        throw UnsupportedOperationException(Packet.CANNOT_DECODE_CLIENTBOUND)
    }

    override fun encode(): ByteBuf {
        val buffer = Unpooled.buffer()

        buffer.writeInt(entityId)
        buffer.writeByte(gameMode.id)
        buffer.writeInt(dimension)
        buffer.writeByte(difficulty)
        buffer.writeByte(1)
        BaseType.STRING.write(buffer, levelType)
        buffer.writeBoolean(reducedDebug)

        return buffer
    }

    override fun processPacket(handler: IHandler) {
        throw UnsupportedOperationException(Packet.CANNOT_PROCESS_CLIENTBOUND)
    }
}
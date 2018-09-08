package com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound

import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.chat.ChatComponent
import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketDisconnect(var reason: ChatComponent) : Packet {
    override fun processPacket(handler: IHandler) {
        throw UnsupportedOperationException(Packet.CANNOT_PROCESS_CLIENTBOUND)
    }

    override fun decode(byteBuf: ByteBuf): Packet {
        throw UnsupportedOperationException(Packet.CANNOT_DECODE_CLIENTBOUND)
    }

    override fun encode(): ByteBuf {
        val b = Unpooled.buffer(4)
        val obj = MinecraftServer.GSON.toJson(reason)
        BaseType.STRING.write(b, obj)
        return b
    }
}
package com.github.xenonminecraft.network.packet.client

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.*
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Handshake
 *
 * ProtocolVersion - VarInt
 * Server Address - String
 * Server Port - Short
 * Next State - VarInt Enum (1 for status, 2 for login)
 */
@PacketInfo(id = 0x00)
class PacketClientHandshake(var protocolVersion: Int? = null, var serverAddress: String? = null, var serverPort: Short? = null, var nextState: NextState? = null) : Packet() {
    override fun decode(byteBuf: ByteBuf) {
        protocolVersion = byteBuf.readVarInt()
        serverAddress = byteBuf.readString()
        serverPort = byteBuf.readShort()
        nextState = NextState.getById(byteBuf.readVarInt())
    }

    enum class NextState(val id: Int) {
        STATUS(1),
        LOGIN(2);

        companion object {
            fun getById(id: Int): NextState = values().first { it.id == id}
        }
    }
}
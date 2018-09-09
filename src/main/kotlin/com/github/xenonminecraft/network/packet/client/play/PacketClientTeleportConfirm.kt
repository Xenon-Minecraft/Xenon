package com.github.xenonminecraft.network.packet.client.play

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.readVarInt
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Teleport_Confirm
 *
 * Teleport Id - VarInt
 *
 */
@PacketInfo(0x00)
class PacketClientTeleportConfirm(var teleportId: Int? = null) : Packet() {

    override fun decode(byteBuf: ByteBuf) {
        teleportId = byteBuf.readVarInt()
    }
}
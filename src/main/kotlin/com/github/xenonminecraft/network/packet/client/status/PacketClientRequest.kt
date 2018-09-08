package com.github.xenonminecraft.network.packet.client.status

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Request
 *
 * Has no fields
 */
@PacketInfo(0x00)
class PacketClientRequest : Packet() {
    override fun decode(byteBuf: ByteBuf) {

    }
}
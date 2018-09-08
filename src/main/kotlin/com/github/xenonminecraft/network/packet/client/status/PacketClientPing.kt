package com.github.xenonminecraft.network.packet.client.status

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Ping
 *
 * Payload: Long
 */
@PacketInfo(0x01)
class PacketClientPing(var payload: Long? = null) : Packet() {
    override fun decode(byteBuf: ByteBuf) {
        payload = byteBuf.readLong()
    }
}
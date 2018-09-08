package com.github.xenonminecraft.network.packet.server.status

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Pong
 *
 * Payload: Long
 */
@PacketInfo(0x01)
class PacketServerPong(var payload: Long) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeLong(payload)

        return buf
    }
}
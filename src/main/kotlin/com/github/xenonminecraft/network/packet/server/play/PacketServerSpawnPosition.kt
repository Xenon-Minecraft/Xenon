package com.github.xenonminecraft.network.packet.server.play

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.xenon.Position
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Spawn_Position
 *
 * Location - Position
 */
@PacketInfo(0x49)
class PacketServerSpawnPosition(var pos: Position) : Packet() {

    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeBytes(pos.encodePosition())

        return buf
    }
}
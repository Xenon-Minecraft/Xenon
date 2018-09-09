package com.github.xenonminecraft.network.packet.server.play

import com.github.xenonminecraft.xenon.Difficulty
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Server_Difficulty
 *
 * Difficulty - Unsigned Byte
 */
@PacketInfo(0x0D)
class PacketServerDifficulty(val difficulty: Difficulty) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeByte(difficulty.id)

        return buf
    }
}
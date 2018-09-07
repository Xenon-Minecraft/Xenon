package com.github.xenonminecraft.network.packet.server.login

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Set_Compression
 *
 * Threshold: VarInt - Maximum size before compressing packets
 */
@PacketInfo(0x03)
class PacketServerSetCompression(var threshold: Int) : Packet() {
    override fun encode(): ByteArray {
        val buf = Unpooled.buffer()
        buf.writeVarInt(threshold)
        return buf.array()
    }
}
package com.github.xenonminecraft.network.packet.server.play

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.writeString
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Plugin_Message_.28clientbound.29
 *
 * Channel - String
 * Data - ByteArray
 */
@PacketInfo(0x19)
class PacketServerPluginMessage(var channel: String, var data: ByteArray) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeString(channel)
        buf.writeVarInt(data.size)
        buf.writeBytes(data)

        return buf
    }
}
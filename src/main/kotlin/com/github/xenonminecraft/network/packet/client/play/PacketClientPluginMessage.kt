package com.github.xenonminecraft.network.packet.client.play

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.readSpecialByteArray
import com.github.xenonminecraft.network.util.readString
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Plugin_Message_.28serverbound.29
 *
 * Channel - String
 * Data - ByteArray
 */
@PacketInfo(0x0A)
class PacketClientPluginMessage(var channel: String? = null, var data: ByteArray? = null) : Packet() {
    override fun decode(byteBuf: ByteBuf) {
        channel = byteBuf.readString()
        data = byteBuf.readSpecialByteArray()
    }
}
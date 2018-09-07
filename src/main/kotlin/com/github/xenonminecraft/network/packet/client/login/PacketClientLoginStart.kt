package com.github.xenonminecraft.network.packet.client.login

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.readString
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Login_Start
 *
 * Player's Name: String(16)
 */
@PacketInfo(0x00)
class PacketClientLoginStart(var username: String? = null) : Packet() {
    override fun decode(byteBuf: ByteBuf) {
        this.username = byteBuf.readString()
    }
}
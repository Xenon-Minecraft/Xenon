package com.github.xenonminecraft.network.packet.server.login

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Disconnect_.28login.29
 *
 * Reason: Chat Packet
 */
@PacketInfo(0x00)
class PacketServerDisconnect : Packet() {
    override fun encode(): ByteArray {
        TODO("encode later")
    }
}
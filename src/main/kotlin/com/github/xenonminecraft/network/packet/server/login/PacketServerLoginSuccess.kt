package com.github.xenonminecraft.network.packet.server.login

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.writeString
import io.netty.buffer.Unpooled
import java.util.*

/**
 * Specification: https://wiki.vg/Protocol#Login_Success
 *
 * UUID: String(36) Full UUID, with hyphens
 * Username: String(16)
 */
@PacketInfo(0x02)
class PacketServerLoginSuccess(var uuid: UUID, var username: String) : Packet() {
    override fun encode(): ByteArray {
        val buf = Unpooled.buffer()
        buf.writeString(uuid.toString())
        buf.writeString(username)
        return buf.array()
    }
}
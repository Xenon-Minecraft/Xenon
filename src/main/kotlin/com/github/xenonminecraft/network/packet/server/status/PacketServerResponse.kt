package com.github.xenonminecraft.network.packet.server.status

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.network.data.ServerListResponse
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.writeString
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Response
 *
 * Json Response - String
 */
@PacketInfo(0x00)
class PacketServerResponse(var response: ServerListResponse) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeString(Xenon.GSON.toJson(response))

        return buf
    }
}
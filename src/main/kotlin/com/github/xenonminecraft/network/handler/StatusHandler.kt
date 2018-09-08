package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.data.ServerListResponse
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.client.status.PacketClientPing
import com.github.xenonminecraft.network.packet.client.status.PacketClientRequest
import com.github.xenonminecraft.network.packet.server.status.PacketServerPong
import com.github.xenonminecraft.network.packet.server.status.PacketServerResponse
import com.google.gson.JsonObject

class StatusHandler(val pc: PlayerConnection) {
    fun handle(p: Packet) {
        when (p) {
            is PacketClientRequest -> {
                val desc = JsonObject().apply {
                    addProperty("text", "Test")
                }
                pc.sendPacket(PacketServerResponse(ServerListResponse(ServerListResponse.Version("1.13", 401), ServerListResponse.Players(20, 0, null), desc)))
            }
            is PacketClientPing -> {
                pc.sendPacket(PacketServerPong(p.payload!!))
            }
        }
    }
}
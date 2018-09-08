package com.theultimatepleb.minecraft.server.protocol.packet.status

import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.chat.ChatColor
import com.theultimatepleb.minecraft.server.chat.ChatComponent
import com.theultimatepleb.minecraft.server.network.handler.IStatusHandler
import com.theultimatepleb.minecraft.server.network.netty.ConnectionHandler
import com.theultimatepleb.minecraft.server.protocol.data.ServerListResponse
import com.theultimatepleb.minecraft.server.protocol.packet.status.clientbound.PacketPong
import com.theultimatepleb.minecraft.server.protocol.packet.status.clientbound.PacketResponse
import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketPing
import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketRequest

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
class StatusHandler(val cH: ConnectionHandler, val minecraftServer: MinecraftServer) : IStatusHandler {
    override fun onDisconnect(text: ChatComponent) {
        TODO("not implemented")
    }

    override fun handlePing(packet: PacketPing) {
        cH.sendPacket(PacketPong(packet.payload))
        cH.channelContext!!.close()
    }

    override fun handleRequest(packet: PacketRequest) {
        cH.sendPacket(PacketResponse(
                ServerListResponse(ServerListResponse.Version("1.12.1"),
                        ServerListResponse.Players(minecraftServer.serverConfiguration.server.serverlist.max, 40),
                        description = ChatComponent(ChatColor.translateAlternateCharCode('&',
                                minecraftServer.serverConfiguration.server.serverlist.motd))
                )
        ))
    }
}
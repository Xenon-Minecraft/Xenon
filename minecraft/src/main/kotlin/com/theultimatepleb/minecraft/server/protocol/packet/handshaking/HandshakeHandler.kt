package com.theultimatepleb.minecraft.server.protocol.packet.handshaking

import com.theultimatepleb.minecraft.server.chat.ChatComponent
import com.theultimatepleb.minecraft.server.network.handler.IHandshakeHandler
import com.theultimatepleb.minecraft.server.network.netty.ConnectionHandler
import com.theultimatepleb.minecraft.server.network.netty.NetworkManager

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
class HandshakeHandler(val cH: ConnectionHandler) : IHandshakeHandler {
    override fun onDisconnect(text: ChatComponent) {
        TODO("not implemented")
    }

    override fun handleHandshake(packet: PacketHandshake) {
        cH.mode = NetworkManager.State.valueOf(packet.state.name)
        cH.connectedIp = packet.address
    }

    override fun handleLegacyHandshake(packet: LegacyPacketHandshake) {
    }
}
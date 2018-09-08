package com.theultimatepleb.minecraft.server.network.handler

import com.theultimatepleb.minecraft.server.protocol.packet.handshaking.LegacyPacketHandshake
import com.theultimatepleb.minecraft.server.protocol.packet.handshaking.PacketHandshake

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
interface IHandshakeHandler : IHandler {
    fun handleHandshake(packet: PacketHandshake)

    fun handleLegacyHandshake(packet: LegacyPacketHandshake)
}
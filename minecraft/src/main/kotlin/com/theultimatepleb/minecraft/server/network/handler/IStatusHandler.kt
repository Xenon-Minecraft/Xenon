package com.theultimatepleb.minecraft.server.network.handler

import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketPing
import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketRequest

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
interface IStatusHandler : IHandler {
    fun handlePing(packet: PacketPing)

    fun handleRequest(packet: PacketRequest)
}
package com.theultimatepleb.minecraft.server.network.handler

import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketEncryptionResponse
import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketLoginStart

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
interface ILoginHandler : IHandler {
    fun handleLoginStart(packet: PacketLoginStart)

    fun handleEncryptionResponse(packet: PacketEncryptionResponse)
}
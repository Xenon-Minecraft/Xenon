package com.theultimatepleb.minecraft.server.protocol.packet

import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.handshaking.LegacyPacketHandshake
import com.theultimatepleb.minecraft.server.protocol.packet.handshaking.PacketHandshake
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketDisconnect
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketEncryptionRequest
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketLoginSuccess
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketSetCompression
import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketEncryptionResponse
import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketLoginStart
import com.theultimatepleb.minecraft.server.protocol.packet.status.clientbound.PacketPong
import com.theultimatepleb.minecraft.server.protocol.packet.status.clientbound.PacketResponse
import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketPing
import com.theultimatepleb.minecraft.server.protocol.packet.status.serverbound.PacketRequest
import io.netty.buffer.ByteBuf
import kotlin.reflect.KClass

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
interface Packet {

    companion object {
        val handshake = mutableMapOf(0x00 to PacketHandshake::class.java,
                0xFE to LegacyPacketHandshake::class.java)
        val clientboundStatus = mutableMapOf(0x00 to PacketResponse::class.java,
                0x01 to PacketPong::class.java)
        val serverboundStatus = mutableMapOf(0x00 to PacketRequest::class.java,
                0x01 to PacketPing::class.java)
        val clientboundLogin = mutableMapOf(0x00 to PacketDisconnect::class.java,
                0x01 to PacketEncryptionRequest::class.java,
                0x02 to PacketLoginSuccess::class.java,
                0x03 to PacketSetCompression::class.java)
        val serverboundLogin = mutableMapOf(0x00 to PacketLoginStart::class.java,
                0x01 to PacketEncryptionResponse::class.java)
        val clientboundPlay = mutableMapOf<Int, Class<out Packet>>()
        val serverboundPlay = mutableMapOf<Int, Class<out Packet>>()

        val CANNOT_ENCODE_SERVERBOUND = "Server can not encode a packet that is received by the client"
        val CANNOT_DECODE_CLIENTBOUND = "Server can not decode a packet that is sent by the server"
        val CANNOT_PROCESS_CLIENTBOUND = "Server can not process a packet that is sent by the server"

    }

    fun decode(byteBuf: ByteBuf): Packet

    fun encode(): ByteBuf

    fun processPacket(handler: IHandler)

}
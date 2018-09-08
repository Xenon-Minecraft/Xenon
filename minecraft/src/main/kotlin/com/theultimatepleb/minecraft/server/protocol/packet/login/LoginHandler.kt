package com.theultimatepleb.minecraft.server.protocol.packet.login

import com.theultimatepleb.minecraft.crystal.GameMode
import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.chat.ChatColor
import com.theultimatepleb.minecraft.server.chat.ChatComponent
import com.theultimatepleb.minecraft.server.mojang.auth.GameProfile
import com.theultimatepleb.minecraft.server.mojang.auth.web.WebRequest
import com.theultimatepleb.minecraft.server.network.handler.ILoginHandler
import com.theultimatepleb.minecraft.server.network.netty.ConnectionHandler
import com.theultimatepleb.minecraft.server.network.netty.NetworkManager
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketDisconnect
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketEncryptionRequest
import com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound.PacketLoginSuccess
import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketEncryptionResponse
import com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound.PacketLoginStart
import com.theultimatepleb.minecraft.server.protocol.packet.play.clientbound.PacketCustomPayload
import com.theultimatepleb.minecraft.server.protocol.packet.play.clientbound.PacketJoinGame
import com.theultimatepleb.minecraft.server.protocol.packet.play.clientbound.PacketPlayerPosLook
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.Unpooled
import java.util.*

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
class LoginHandler(val cH: ConnectionHandler,val minecraftServer: MinecraftServer) : ILoginHandler {

    val verifyToken = minecraftServer.encryptionManager.generateByteArray(4)
    lateinit var gameProfile: GameProfile

    override fun onDisconnect(text: ChatComponent) {
        TODO("not implemented")
    }

    override fun handleLoginStart(packet: PacketLoginStart) {
        cH.gameProfile = GameProfile(packet.name!!)
        gameProfile = cH.gameProfile!!
        cH.sendPacket(PacketEncryptionRequest(verifyToken = verifyToken))
    }

    override fun handleEncryptionResponse(packet: PacketEncryptionResponse) {
        if(!Arrays.equals(verifyToken, packet.getToken(minecraftServer.encryptionManager.privateKey))) {
            disconnect("Verify Token does not match!")
            return
        }

        cH.sharedSecret = packet.getSecretKey(minecraftServer.encryptionManager.privateKey)
        val serverHash = minecraftServer.encryptionManager.hashToString(minecraftServer.encryptionManager
                .getServerIdHash("", minecraftServer.encryptionManager.publicKey, cH.sharedSecret!!))

        val verifyOnline = WebRequest.verifyJoin(gameProfile.name, serverHash, cH.address.hostString)
        if(verifyOnline == null) {
            disconnect("Not authenticated with Mojang servers!")
            return
        }

        cH.enableEncryption()
        cH.sendPacket(PacketLoginSuccess(verifyOnline))

        MinecraftServer.LOGGER.info("Player ${verifyOnline.name}(${verifyOnline.uuid}) " +
                "connected from ${cH.address.hostString}:${cH.address.port}")

        cH.mode = NetworkManager.State.PLAY

        cH.sendPacket(PacketJoinGame(0, GameMode.SURVIVAL, 0, 2))
        announceBrand()
    }

    private fun announceBrand() {
        val b = Unpooled.buffer()
        BaseType.STRING.write(b, "${MinecraftServer.NAME} ${MinecraftServer.VERSION}")

        cH.sendPacket(PacketCustomPayload("MC|Brand", b))
    }

    private fun disconnect(text: String) {
        cH.sendPacket(PacketDisconnect(ChatComponent(text, color = ChatColor.RED.nameL())))
        cH.channelContext!!.close()
    }

}
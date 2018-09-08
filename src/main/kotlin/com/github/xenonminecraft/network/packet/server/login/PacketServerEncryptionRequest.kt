package com.github.xenonminecraft.network.packet.server.login

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.writeString
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Encryption_Request
 *
 * Server ID: String(20) - Always empty
 * Public Key Length: VarInt
 * Public Key: ByteArray
 * Verify Token Length: VarInt
 * Verify Token: ByteArray
 */
@PacketInfo(0x01)
class PacketServerEncryptionRequest(var publicKey: ByteArray, var verifyToken: ByteArray) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeString("")
        buf.writeVarInt(publicKey.size)
        buf.writeBytes(publicKey)
        buf.writeVarInt(verifyToken.size)
        buf.writeBytes(verifyToken)

        return buf
    }
}
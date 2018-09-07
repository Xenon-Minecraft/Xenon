package com.github.xenonminecraft.network.packet.client.login

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.readVarInt
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Encryption_Response
 *
 * Shared Secret Length: VarInt
 * Shared Secret: ByteArray
 * Verify Token Length: VarInt
 * Verify Token: ByteArray
 */
@PacketInfo(0x01)
class PacketClientEncryptionResponse(var sharedSecret: ByteArray? = null,
                                     var verifyToken: ByteArray? = null) : Packet() {
    override fun decode(byteBuf: ByteBuf) {
        var length = byteBuf.readVarInt()
        sharedSecret = byteBuf.readBytes(length).array()
        length = byteBuf.readVarInt()
        verifyToken = byteBuf.readBytes(length).array()
    }
}
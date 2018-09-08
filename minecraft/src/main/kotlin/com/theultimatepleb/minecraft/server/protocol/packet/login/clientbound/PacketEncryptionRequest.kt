package com.theultimatepleb.minecraft.server.protocol.packet.login.clientbound

import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.security.PublicKey

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketEncryptionRequest(var serverId: String = "",
                              var publicKey: PublicKey = MinecraftServer.instance!!.encryptionManager.publicKey,
                              var verifyToken: ByteArray) : Packet {

    override fun processPacket(handler: IHandler) {
        throw UnsupportedOperationException(Packet.CANNOT_PROCESS_CLIENTBOUND)
    }

    override fun decode(byteBuf: ByteBuf): Packet {
        throw UnsupportedOperationException(Packet.CANNOT_DECODE_CLIENTBOUND)
    }

    override fun encode(): ByteBuf {
        val b = Unpooled.buffer()

        BaseType.STRING.write(b, serverId)
        BaseType.BYTE_ARRAY.write(b, publicKey.encoded)
        BaseType.BYTE_ARRAY.write(b, verifyToken)

        return b
    }
}
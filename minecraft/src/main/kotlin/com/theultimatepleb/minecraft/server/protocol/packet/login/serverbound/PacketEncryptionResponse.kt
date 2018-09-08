package com.theultimatepleb.minecraft.server.protocol.packet.login.serverbound

import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.network.handler.IHandler
import com.theultimatepleb.minecraft.server.network.handler.ILoginHandler
import com.theultimatepleb.minecraft.server.protocol.packet.Packet
import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import java.security.PrivateKey

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class PacketEncryptionResponse(var sharedSecretEncrypted: ByteArray? = null,
                               var verifyTokenEncrypted: ByteArray? = null) : Packet {
    override fun processPacket(handler: IHandler) {
        (handler as ILoginHandler).handleEncryptionResponse(this)
    }


    fun getToken(key: PrivateKey) = MinecraftServer.instance!!.encryptionManager.decryptData(key, verifyTokenEncrypted!!)

    fun getSecretKey(key: PrivateKey) = MinecraftServer.instance!!.encryptionManager.decryptSharedKey(key, sharedSecretEncrypted!!)

    override fun decode(byteBuf: ByteBuf): Packet {
        this.sharedSecretEncrypted = BaseType.BYTE_ARRAY.read(byteBuf)
        this.verifyTokenEncrypted = BaseType.BYTE_ARRAY.read(byteBuf)

        return this
    }

    override fun encode(): ByteBuf {
        throw UnsupportedOperationException(Packet.CANNOT_ENCODE_SERVERBOUND)
    }

}
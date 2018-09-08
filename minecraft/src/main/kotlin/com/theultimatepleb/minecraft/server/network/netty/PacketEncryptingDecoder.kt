package com.theultimatepleb.minecraft.server.network.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import javax.crypto.Cipher

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
class PacketEncryptingDecoder(val cipher: Cipher) : ByteToMessageDecoder() {

    private val codec = PacketEncryptionTranslator(cipher)

    override fun decode(ctx: ChannelHandlerContext, inBuf: ByteBuf, out: MutableList<Any>) {
        out.add(codec.decipher(ctx, inBuf))
    }
}
package com.github.xenonminecraft.network.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import javax.crypto.Cipher

class PacketEncryptingDecoder(val cipher: Cipher) : ByteToMessageDecoder() {

    private val codec = PacketEncryptionTranslator(cipher)

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        out.add(codec.decipher(ctx, buf))
    }
}
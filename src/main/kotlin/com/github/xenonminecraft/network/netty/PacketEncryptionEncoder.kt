package com.github.xenonminecraft.network.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import javax.crypto.Cipher

class PacketEncryptingEncoder(val cipher: Cipher) : MessageToByteEncoder<ByteBuf>() {

    private val codec = PacketEncryptionTranslator(cipher)

    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
        codec.cipher(msg, out)
    }
}
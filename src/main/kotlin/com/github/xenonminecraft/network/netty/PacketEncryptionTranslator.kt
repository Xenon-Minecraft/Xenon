package com.github.xenonminecraft.network.netty

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import javax.crypto.Cipher

class PacketEncryptionTranslator(val cipher: Cipher) {

    fun decipher(ctx: ChannelHandlerContext, buf: ByteBuf): ByteBuf {
        val readable = buf.readableBytes()
        val array = buf.array()

        val heapBuffer = ctx.alloc().heapBuffer(cipher.getOutputSize(readable))
        heapBuffer.writerIndex(cipher.update(array, 0, readable, heapBuffer.array(), heapBuffer.arrayOffset()))

        return heapBuffer
    }

    fun cipher(inBuf: ByteBuf, outBuf: ByteBuf) {
        val readable = inBuf.readableBytes()
        val array = inBuf.array()
        val size = cipher.getOutputSize(readable)

        val buff = ByteArray(size)

        outBuf.writeBytes(buff, 0, cipher.update(array, 0, readable, buff))
    }
}
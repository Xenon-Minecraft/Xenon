package com.github.xenonminecraft.network.netty

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import javax.crypto.Cipher

class PacketEncryptionTranslator(val cipher: Cipher) {

    fun decipher(ctx: ChannelHandlerContext, buf: ByteBuf): ByteBuf {
        val readable = buf.readableBytes()
        val array = Unpooled.copiedBuffer(buf).array()

        val heapBuffer = ctx.alloc().heapBuffer(cipher.getOutputSize(readable))
        heapBuffer.writerIndex(cipher.update(array, 0, readable, heapBuffer.array(), heapBuffer.arrayOffset()))

        return heapBuffer
    }

    fun cipher(inBuf: ByteBuf, outBuf: ByteBuf) {
        try {
            val readable = inBuf.readableBytes()
            val array = Unpooled.copiedBuffer(inBuf).array()
            val size = cipher.getOutputSize(readable)

            val buff = ByteArray(size)

            outBuf.writeBytes(buff, 0, cipher.update(array, 0, readable, buff))
        } catch(e: Exception) {
            e.printStackTrace()
        }

    }
}
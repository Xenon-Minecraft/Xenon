/*
 * MIT License
 *
 * Copyright (c) 2018 Xenon and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
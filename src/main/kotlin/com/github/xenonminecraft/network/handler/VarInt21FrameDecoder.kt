package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.network.util.readVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.CorruptedFrameException

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
class VarInt21FrameDecoder : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        buf.markReaderIndex()
        val bytes = ByteArray(3)

        for(i in bytes.indices) {
            if(!buf.isReadable) {
                buf.resetReaderIndex()
                return
            }

            bytes[i] = buf.readByte()

            if (bytes[i] >= 0) {
                val unpooledBuf = Unpooled.wrappedBuffer(bytes)

                try {
                    val length = unpooledBuf.readVarInt()
                    if(buf.readableBytes() >= length) {
                        out.add(buf.readBytes(length))
                        return
                    }

                    buf.resetReaderIndex()
                } finally {
                    unpooledBuf.release()
                }

                return
            }
        }

        throw CorruptedFrameException("Length is wider than 21-bits")
    }
}
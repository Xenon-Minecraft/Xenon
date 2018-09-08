package com.theultimatepleb.minecraft.server.network.netty

import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import io.netty.handler.codec.CorruptedFrameException

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
class Varint21FrameDecoder : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext?, inBuf: ByteBuf?, out: MutableList<Any>?) {
        inBuf!!.markReaderIndex()
        val bytes = ByteArray(3)

        for(i in bytes.indices) {
            if(!inBuf.isReadable) {
                inBuf.resetReaderIndex()
                return
            }

            bytes[i] = inBuf.readByte()

            if (bytes[i] >= 0) {
                val unpooledBuf = Unpooled.wrappedBuffer(bytes)

                try {
                    val length = BaseType.VAR_INT.read(unpooledBuf)
                    if(inBuf.readableBytes() >= length) {
                        out!!.add(inBuf.readBytes(length))
                        return
                    }

                    inBuf.resetReaderIndex()
                } finally {
                    unpooledBuf.release()
                }

                return
            }
        }

        throw CorruptedFrameException("Length is wider than 21-bits")
    }
}
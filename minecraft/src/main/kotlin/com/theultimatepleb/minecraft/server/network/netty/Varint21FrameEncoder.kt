package com.theultimatepleb.minecraft.server.network.netty

import com.theultimatepleb.minecraft.server.protocol.type.BaseType
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
class Varint21FrameEncoder : MessageToByteEncoder<ByteBuf>() {

    companion object {
        val MAX_FIT = 3
    }

    override fun encode(ctx: ChannelHandlerContext?, msg: ByteBuf?, out: ByteBuf?) {
        val readable = msg!!.readableBytes()
        val readableSize = BaseType.VAR_INT.size(readable)

        if(readableSize > MAX_FIT) {
            throw IllegalArgumentException("Unable to fit $readable into ${MAX_FIT}")
        }

        out!!.ensureWritable(readable + readableSize)
        BaseType.VAR_INT.write(out, readable)
        out.writeBytes(msg, msg.readerIndex(), readable)

    }
}
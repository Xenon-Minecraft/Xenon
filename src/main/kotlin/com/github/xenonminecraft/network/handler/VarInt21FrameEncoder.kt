package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.network.util.sizeOfVarInt
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
class VarInt21FrameEncoder : MessageToByteEncoder<ByteBuf>() {

    companion object {
        val MAX_FIT = 3
    }

    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
        val readable = msg.readableBytes()
        val readableSize = readable.sizeOfVarInt()

        if(readableSize > MAX_FIT) {
            throw IllegalArgumentException("Unable to fit $readable into ${MAX_FIT}")
        }

        out.ensureWritable(readable + readableSize)
        out.writeVarInt(readable)
        out.writeBytes(msg, msg.readerIndex(), readable)
    }
}
package com.github.xenonminecraft.network.netty

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.util.readVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import java.util.zip.Inflater

class MinecraftPacketDecompressor(val pc: PlayerConnection) : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        if(buf.refCnt() == 0)
            return

        if (pc.compressionThreshold < 0) {
            out.add(buf)
            return
        }

        val decompressedSize = buf.readVarInt()
        if (decompressedSize == 0) {
            out.add(buf)
            return
        }
        val inflater = Inflater()
        inflater.setInput(Unpooled.copiedBuffer(buf).array())
        val result = ByteArray(decompressedSize)
        if (inflater.inflate(result) != decompressedSize)
            throw IllegalArgumentException("the uncompressed data is not correctly labelled")
        out.add(Unpooled.copiedBuffer(result))

    }
}
package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.util.readVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder
import org.apache.commons.compress.compressors.CompressorStreamFactory
import java.io.ByteArrayInputStream

class MinecraftPacketDecompressor(val pc: PlayerConnection) : ByteToMessageDecoder() {
    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        if(pc.compressionThreshold < 0) {
            out.add(buf)
            return
        }

        val decompressedSize = buf.readVarInt()
        if(decompressedSize == 0) {
            out.add(buf)
            return
        }

        //Decompress data
        val bais = ByteArrayInputStream(buf.array())
        val input = CompressorStreamFactory()
                .createCompressorInputStream(CompressorStreamFactory.DEFLATE64, bais)
        if(input.uncompressedCount != decompressedSize.toLong()) {
            Xenon.LOGGER.error("Uncompressed Count does not match decompressed size, corrupted data?")
            return
        }

        out.add(Unpooled.copiedBuffer(input.readBytes()))
        input.close()
        bais.close()
    }
}
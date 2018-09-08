package com.github.xenonminecraft.network.netty

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import org.apache.commons.compress.compressors.CompressorStreamFactory
import java.io.ByteArrayOutputStream

class MinecraftPacketCompressor(val pc: PlayerConnection) : MessageToByteEncoder<ByteBuf>() {
    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
        val size = msg.readableBytes()
        if (size < pc.compressionThreshold) {
            out.writeVarInt(0)
            out.writeBytes(msg)
        }

        val output = ByteArrayOutputStream()
        val compress = CompressorStreamFactory()
                .createCompressorOutputStream(CompressorStreamFactory.DEFLATE64, output)
        compress.write(msg.array())

        out.writeVarInt(size)
        out.writeBytes(output.toByteArray())
    }
}
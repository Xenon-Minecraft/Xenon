package com.github.xenonminecraft.network.netty

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import java.io.ByteArrayOutputStream
import java.util.zip.DeflaterOutputStream

class MinecraftPacketCompressor(val pc: PlayerConnection) : MessageToByteEncoder<ByteBuf>() {
    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
        val size = msg.readableBytes()
        if (size < pc.compressionThreshold) {
            out.writeVarInt(0)
            out.writeBytes(msg)
            return
        }
        try {
            val compressedData = ByteArrayOutputStream().also {
                DeflaterOutputStream(it).use {
                    it.write(Unpooled.copiedBuffer(msg).array())
                }
            }.toByteArray()

            out.writeVarInt(size)
            out.writeBytes(compressedData)
        } catch(e: Exception) {
            e.printStackTrace()
        }

    }
}
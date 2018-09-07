package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.network.PlayerConnection
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class MinecraftPacketDecoder(val pc: PlayerConnection) : ByteToMessageDecoder() {

    override fun decode(ctx: ChannelHandlerContext, inBuf: ByteBuf, out: MutableList<Any>) {
        if (inBuf.readableBytes() == 0)
            return



    }
}
package com.github.xenonminecraft.network

import com.github.xenonminecraft.network.netty.MinecraftPacketDecoder
import com.github.xenonminecraft.network.netty.MinecraftPacketEncoder
import com.github.xenonminecraft.network.netty.VarInt21FrameDecoder
import com.github.xenonminecraft.network.netty.VarInt21FrameEncoder
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class XenonServerInitialiser : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val cm = PlayerConnection(ch.remoteAddress(), ch)
        val pipeline = ch.pipeline()

        //Handle legacy handshake packet ;), courtesy of proximyst
        pipeline.addFirst("legacy ping packet handler", object : ChannelInboundHandlerAdapter() {
            override fun channelRead(ctx: ChannelHandlerContext, msg: Any?) {
                if (msg !is ByteBuf) return
                val buf = msg.copy(0, 2)
                if (buf.readByte() == 0xFE.toByte()) {
                    buf.release()
                    msg.release()
                    ctx.close()
                }
            }
        })

        //Decryption will be here
        pipeline.addLast("splitter", VarInt21FrameDecoder())
        //Decompressor will be here when compression is enabled
        pipeline.addLast("decoder", MinecraftPacketDecoder(cm))

        //Encryption will be here
        pipeline.addLast("prepender", VarInt21FrameEncoder())
        //Compressor will be here
        pipeline.addLast("encoder", MinecraftPacketEncoder())

    }
}
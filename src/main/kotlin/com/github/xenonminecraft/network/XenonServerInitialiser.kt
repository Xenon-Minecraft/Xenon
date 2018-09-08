package com.github.xenonminecraft.network

import com.github.xenonminecraft.network.netty.MinecraftPacketDecoder
import com.github.xenonminecraft.network.netty.MinecraftPacketEncoder
import com.github.xenonminecraft.network.netty.VarInt21FrameDecoder
import com.github.xenonminecraft.network.netty.VarInt21FrameEncoder
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel

class XenonServerInitialiser : ChannelInitializer<SocketChannel>() {
    override fun initChannel(ch: SocketChannel) {
        val cm = PlayerConnection(ch.remoteAddress(), ch)
        val pipeline = ch.pipeline()
        //Decryption will be here
        pipeline.addLast("splitter", VarInt21FrameDecoder())
        //Decompressor will be here when compression is enabled
        pipeline.addLast("decoder", MinecraftPacketDecoder(cm))

        pipeline.addLast("prepender", VarInt21FrameEncoder())
        //Compressor will be here
        pipeline.addLast("encoder", MinecraftPacketEncoder())
        //Encryption will be here

        //PACKET HANDLER
    }
}
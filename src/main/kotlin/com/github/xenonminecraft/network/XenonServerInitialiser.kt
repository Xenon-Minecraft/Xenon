/*
 * MIT License
 *
 * Copyright (c) 2018 Xenon and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

        //TODO: Handle legacy handshake packet

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
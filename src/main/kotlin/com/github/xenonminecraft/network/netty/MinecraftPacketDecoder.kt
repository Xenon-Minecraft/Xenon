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

package com.github.xenonminecraft.network.netty

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.PlayerConnection.State
import com.github.xenonminecraft.network.handler.LoginHandler
import com.github.xenonminecraft.network.handler.PlayHandler
import com.github.xenonminecraft.network.handler.StatusHandler
import com.github.xenonminecraft.network.packet.PacketManager
import com.github.xenonminecraft.network.packet.client.PacketClientHandshake
import com.github.xenonminecraft.network.util.readVarInt
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageDecoder

class MinecraftPacketDecoder(val pc: PlayerConnection) : ByteToMessageDecoder() {

    var state: State
        get() = pc.state
        set(status) { pc.state = status}

    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        if (buf.readableBytes() == 0)
            return


        val id: Int
        try {
            id = buf.readVarInt()
        } catch(e: Exception) {
            return
        }

        when(state) {
            State.NONE -> {

                if(id != 0)
                    return

                val handshake = PacketClientHandshake()
                handshake.decode(buf)

                pc.protocolVersion = handshake.protocolVersion!!

                state = handshake.nextState!!
            }
            State.LOGIN -> {
                try {
                    val cls = PacketManager.loginPackets!![id] ?: return
                    val p = cls.newInstance()
                    p.decode(buf)
                    LoginHandler(pc).handle(p)
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
            State.STATUS -> {
                try {
                    val cls = PacketManager.statusPackets!![id] ?: return
                    val p = cls.newInstance()
                    p.decode(buf)
                    StatusHandler(pc).handle(p)
                } catch(e: Exception) {
                    e.printStackTrace()
                }

            }
            State.PLAY -> {
                try {
                    val cls = PacketManager.playPackets!![id] ?: return
                    val p = cls.newInstance()
                    p.decode(buf)
                    PlayHandler(pc).handle(p)
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }
}
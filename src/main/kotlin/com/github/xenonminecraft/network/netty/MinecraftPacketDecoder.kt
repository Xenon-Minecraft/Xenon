package com.github.xenonminecraft.network.netty

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.PlayerConnection.State
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

        when(state) {
            State.NONE -> {
                val id = buf.readVarInt()
                if(id != 0)
                    return

                val handshake = PacketClientHandshake()
                handshake.decode(buf)

                pc.protocolVersion = handshake.protocolVersion!!

                state = handshake.nextState!!
            }
        }

    }
}
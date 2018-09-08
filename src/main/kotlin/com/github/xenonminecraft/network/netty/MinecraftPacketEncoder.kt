package com.github.xenonminecraft.network.netty

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder

class MinecraftPacketEncoder : MessageToByteEncoder<Packet>() {
    override fun encode(ctx: ChannelHandlerContext, msg: Packet, out: ByteBuf) {
        val id = msg::class.java.getAnnotation(PacketInfo::class.java).id
        val data = msg.encode()
        val buf = Unpooled.buffer()

        buf.writeVarInt(id)
        buf.writeBytes(data)

        out.writeBytes(buf)
    }
}
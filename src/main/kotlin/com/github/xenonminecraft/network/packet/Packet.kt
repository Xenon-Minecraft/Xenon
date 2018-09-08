package com.github.xenonminecraft.network.packet

import io.netty.buffer.ByteBuf

abstract class Packet {
    open fun decode(byteBuf: ByteBuf) {
        throw UnsupportedOperationException("Unable to decode this packet")
    }
    open fun encode(): ByteBuf {
        throw UnsupportedOperationException("Unable to encode this packet")
    }
}
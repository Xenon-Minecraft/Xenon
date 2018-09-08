package com.theultimatepleb.minecraft.server.protocol.type

import io.netty.buffer.ByteBuf
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by Sebastian Agius on 16/08/2017.
 */
class LongType : BaseType<Long>() {
    override fun write(byteBuf: ByteBuf, w: Long) {
        byteBuf.writeLong(w)
    }

    override fun read(byteBuf: ByteBuf): Long {
        val bytes = ByteArray(8)
        byteBuf.readBytes(bytes)
        val buffer = ByteBuffer.allocate(8)
                .order(ByteOrder.BIG_ENDIAN)
                .put(bytes)
        return buffer.getLong(0)
    }
}
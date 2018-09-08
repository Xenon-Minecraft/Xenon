package com.theultimatepleb.minecraft.server.protocol.type

import io.netty.buffer.ByteBuf
import kotlin.experimental.and
import kotlin.experimental.or

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class VarIntType : BaseType<Int>() {
    fun size(w: Int): Int {
        return (1..4).firstOrNull { w and (-1 shl it * 7) == 0 }
                ?: 5
    }

    /**
     * Writes an Int as a VarInt to the ByteBuf
     * @param byteBuf the ByteBuf to write to
     * @param w the Integer to write as a VarInt
     */
    override fun write(byteBuf: ByteBuf, w: Int) {
        var i = w
        while ((i and -128) != 0) {
            byteBuf.writeByte(i and 127 or 128)
            i = i ushr 7
        }

        byteBuf.writeByte(i)
    }

    /**
     * Read a VarInt from the ByteBuf
     * @param byteBuf the ByteBuf to read from
     * @return the VarInt read from the ByteBuf as an Integer
     */
    override fun read(byteBuf: ByteBuf): Int {
        var i = 0
        var j = 0
        while (true) {
            val k = byteBuf.readByte()
            i = i or ((k and 127).toInt() shl j++ * 7)
            if (j > 5) return -1
            if (k and 128.toByte() != 128.toByte()) break
        }
        return i
    }
}
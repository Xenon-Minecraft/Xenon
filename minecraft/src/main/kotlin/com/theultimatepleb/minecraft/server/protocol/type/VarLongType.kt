package com.theultimatepleb.minecraft.server.protocol.type

import io.netty.buffer.ByteBuf
import kotlin.experimental.and
import kotlin.experimental.or

/**
 * Created by Sebastian Agius on 16/08/2017.
 */
class VarLongType : BaseType<Long>() {

    override fun write(byteBuf: ByteBuf, w: Long) {
        var value = w
        do {
            var temp = (value and 127).toInt()
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value = value ushr 7
            if (w != 0L) {
                temp = temp or 128
            }
            byteBuf.writeByte(temp)
        } while (value != 0L)
    }

    override fun read(byteBuf: ByteBuf): Long {
        var numRead = 0
        var result: Long = 0
        var read: Byte
        do {
            read = byteBuf.readByte()
            val value = read and 127
            result = result or (value.toInt() shl 7 * numRead).toLong()

            numRead++
            if (numRead > 10) {
                throw RuntimeException("VarLong is too big")
            }
        } while (read.toInt() and 128 != 0)

        return result
    }
}
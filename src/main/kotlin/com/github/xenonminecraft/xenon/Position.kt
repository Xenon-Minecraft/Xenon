package com.github.xenonminecraft.xenon

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import kotlin.math.pow

class Position(var x: Int = 0, var y: Int = 0, var z: Int = 0) {
    fun encodePosition(): ByteArray {
        checkNotNull(x)
        checkNotNull(y)
        checkNotNull(z)

        val buf = Unpooled.buffer()
        buf.writeLong((((x and 0x3FFFFFF) shl 38) or ((y and 0xFFF) shl 26) or (z and 0x3FFFFFF)).toLong())
        return buf.array()
    }

    fun decodePosition(bytes: ByteBuf) {
        val r = bytes.readLong().toInt()
        x = r shr 38
        y = r shr 26 and 0xFFF
        z = r shl 38 shr 38

        //if (x >= 2F.pow(25)) { x -= 2F.pow(26).toInt() }
        //if (y >= 2F.pow(11)) { y -= 2F.pow(12).toInt() }
        //if (z >= 2F.pow(25)) { z -= 2F.pow(26).toInt() }
    }
}
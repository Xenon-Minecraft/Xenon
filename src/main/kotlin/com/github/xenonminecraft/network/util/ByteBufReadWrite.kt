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

package com.github.xenonminecraft.network.util

import com.github.xenonminecraft.xenon.Position
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import kotlin.experimental.and


fun ByteBuf.readVarInt(): Int {
    try {

    } catch(e: Exception) {

    }
    var i = 0
    var j = 0
    while (true) {
        val k = readByte()
        i = i or ((k and 127).toInt() shl j++ * 7)
        if (j > 5) return -1
        if (k and 128.toByte() != 128.toByte()) break
    }
    return i
}

fun ByteBuf.readSpecialByteArray(): ByteArray {
    return Unpooled.copiedBuffer(readBytes(readVarInt())).array()
}

fun ByteBuf.readVarLong(): Long {
    var numRead = 0
    var result: Long = 0
    var read: Byte
    do {
        read = readByte()
        val value = read and 127
        result = result or (value.toInt() shl (7 * numRead)).toLong()

        numRead++
        if (numRead > 10) {
            throw RuntimeException("VarLong is too big")
        }
    } while (read and (128).toByte() != (0).toByte())

    return result
}

fun ByteBuf.writeVarInt(num: Int) {
    var i = num
    while ((i and -128) != 0) {
        writeByte(i and 127 or 128)
        i = i ushr 7
    }

    writeByte(i)
}

fun Int.sizeOfVarInt(): Int {
    return (1..4).firstOrNull { this and (-1 shl it * 7) == 0 }
            ?: 5
}

fun ByteBuf.writeVarLong(num: Long) {
    var value = num
    do {
        var temp = (value and 127).toInt()
        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
        value = value ushr 7
        if (value != 0L) {
            temp = temp or 128
        }
        writeByte(temp)
    } while (value != 0L)
}

fun ByteBuf.readString(): String {
    val size = readVarInt()
    val array = ByteArray(size)
    readBytes(array)
    val string = String(array)
    return string
}

fun ByteBuf.writeString(str: String) {
    writeVarInt(str.length)
    writeBytes(str.toByteArray())
}

fun ByteBuf.writePosition(pos: Position) {
    writeLong(pos.toLong())
}

fun ByteBuf.readPosition(): Position = Position.fromLong(readLong())

fun ByteBuf.writeDoubles(vararg d: Double) {
    d.forEach { writeDouble(it) }
}

fun ByteBuf.writeFloats(vararg f: Float) {
    f.forEach { writeFloat(it) }
}
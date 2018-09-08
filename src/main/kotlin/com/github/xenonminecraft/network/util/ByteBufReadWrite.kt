package com.github.xenonminecraft.network.util

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import kotlin.experimental.and


fun ByteBuf.readVarInt(): Int {
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

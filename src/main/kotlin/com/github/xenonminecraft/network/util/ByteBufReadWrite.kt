package com.github.xenonminecraft.network.util

import io.netty.buffer.ByteBuf
import kotlin.experimental.and


fun ByteBuf.readVarInt(): Int {
    var numRead = 0
    var result = 0
    var read: Byte
    do {
        read = readByte()
        val value = read and 127
        val a = (value.toInt() shl (7 * numRead))
        result = result or a

        numRead++
        if (numRead > 5) {
            throw RuntimeException("VarInt is too big")
        }
    } while (read and (128).toByte() != (0).toByte())

    return result
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
    var value = num
    do {
        var temp = (value and 127)
        // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
        value = value ushr 7
        if (value != 0) {
            temp = temp or 128
        }
        writeByte(temp)
    } while (value != 0)
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
    val string = String(readBytes(size).array())
    return string
}

fun ByteBuf.writeString(str: String) {
    writeVarInt(str.length)
    writeBytes(str.toByteArray())
}

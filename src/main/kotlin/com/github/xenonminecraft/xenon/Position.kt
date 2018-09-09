package com.github.xenonminecraft.xenon

class Position(var x: Int = 0, var y: Int = 0, var z: Int = 0) {
    fun toLong(): Long = (((x and 0x3FFFFFF) shl 38) or ((y and 0xFFF) shl 26) or (z and 0x3FFFFFF)).toLong()

    companion object {
        @JvmStatic
        fun fromLong(r: Long) = Position((r shr 38).toInt(), (r shr 26 and 0xFFF).toInt(), (r shl 38 shr 38).toInt())
    }
}
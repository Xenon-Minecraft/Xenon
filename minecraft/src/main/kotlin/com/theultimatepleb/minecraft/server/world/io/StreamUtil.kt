package com.theultimatepleb.minecraft.server.world.io

import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStream

/**
 * Created by Sebastian Agius on 13/09/2017.
 */
open class StreamUtil {

    fun DataOutputStream.writeByteArray(baos: ByteArrayOutputStream) {
        writeByteArray(baos.toByteArray())
    }

    fun DataOutputStream.writeByteArray(array: ByteArray) {
        this.writeInt(array.size)
        this.write(array)
    }

    fun DataInputStream.readByteArray(): ByteArray = readBytes(this.readInt())

    private fun InputStream.readBytes(size: Int): ByteArray {
        val array = ByteArray(size)
        for (i in array.indices) {
            array[i] = this.read().toByte()
        }

        return array
    }

}
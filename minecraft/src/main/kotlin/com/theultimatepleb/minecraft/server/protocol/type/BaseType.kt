package com.theultimatepleb.minecraft.server.protocol.type

import io.netty.buffer.ByteBuf
import mu.KotlinLogging

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
abstract class BaseType<T> {

    companion object {
        val LOGGER = KotlinLogging.logger {}

        val VAR_INT = VarIntType()
        val VAR_LONG = VarLongType()
        val STRING = StringType()
        val LONG = LongType()
        val BYTE_ARRAY = ByteArrayType()
    }

    abstract fun write(byteBuf: ByteBuf, w: T)

    abstract fun read(byteBuf: ByteBuf): T

    /**
     * Calculates how many bytes are left in the ByteBuf
     * @return the amount of bytes left from the current ByteBuf#readerIndex
     */
    fun ByteBuf.bytesLeft(): Int {
        return this.capacity() - this.readerIndex()
    }

    /**
     * Makes sure that the available bytes left is enough to be read
     * @param byteBuf the ByteBuf to check
     * @param amount the amount of bytes to check if available
     * @throws IllegalStateException if the ByteBuf does not have enough bytes
     */
    fun ByteBuf.ensure(amount: Int) {
        val left = this.bytesLeft()
        if(left >= amount)
            return
        throw IllegalStateException("buffer doesn't contain enough bytes")
    }
}
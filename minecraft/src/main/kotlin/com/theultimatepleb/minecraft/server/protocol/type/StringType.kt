package com.theultimatepleb.minecraft.server.protocol.type

import io.netty.buffer.ByteBuf
import java.nio.charset.Charset
import kotlin.experimental.and

/**
 * Created by Sebastian Agius on 15/08/2017.
 */
class StringType : BaseType<String>() {
    /**
     * Writes a String to the ByteBuf
     * @param byteBuf the buffer to write to
     * @param w the string to write to the buffer
     * @throws IllegalArgumentException if the string length is over Short.MAX_VALUE
     */
    override fun write(byteBuf: ByteBuf, w: String) {
        val bytes = w.toByteArray(Charsets.UTF_8)
        val length = bytes.size
        if(length >= Short.MAX_VALUE)
            throw IllegalArgumentException("(write) String length cannot be bigger than Short.MAX_VALUE ( ${Short.MAX_VALUE} )")
        BaseType.VAR_INT.write(byteBuf, length)
        byteBuf.writeBytes(bytes)
    }

    /**
     * Reads a String from the ByteBuf
     * @param byteBuf the ByteBuf to read from
     * @throws IllegalArgumentException if the string length is over Short.MAX_VALUE
     * @return the String read from the ByteBuf
     */
    override fun read(byteBuf: ByteBuf): String {
        val length = BaseType.VAR_INT.read(byteBuf)
        if(length >= Short.MAX_VALUE)
            throw IllegalArgumentException("(read) String length cannot be bigger than Short.MAX_VALUE ( ${Short.MAX_VALUE} )")
        byteBuf.ensure(length)

        val buffer = ByteArray(length)
        byteBuf.readBytes(buffer)
        return String(buffer, Charsets.UTF_8)
    }
}
package com.theultimatepleb.minecraft.server.protocol.type

import io.netty.buffer.ByteBuf

/**
 * Created by Sebastian Agius(TheUltimatePleb) on 12/09/17.
 */
class ByteArrayType : BaseType<ByteArray>() {
    override fun write(byteBuf: ByteBuf, w: ByteArray) {
        BaseType.VAR_INT.write(byteBuf, w.size)
        byteBuf.writeBytes(w)
    }

    override fun read(byteBuf: ByteBuf): ByteArray {
        val array = ByteArray(BaseType.VAR_INT.read(byteBuf))
        byteBuf.readBytes(array)
        return array
    }
}
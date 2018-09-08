package com.theultimatepleb.minecraft.server.world.io.impl

import com.theultimatepleb.minecraft.server.world.io.IRegion
import com.theultimatepleb.minecraft.server.world.io.StreamUtil
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * Created by Sebastian Agius on 13/09/2017.
 */
class IORegion : StreamUtil(), IRegion {
    override fun readChunk(dis: DataInputStream) {
        val cdin = DataInputStream(ByteArrayInputStream(dis.readByteArray()))
    }

    override fun writeChunk(dos: DataOutputStream) {
        TODO("not implemented")
    }

    override fun read() {
        TODO("not implemented")
    }

    override fun write() {
        TODO("not implemented")
    }
}
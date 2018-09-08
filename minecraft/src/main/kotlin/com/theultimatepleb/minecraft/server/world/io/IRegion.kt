package com.theultimatepleb.minecraft.server.world.io

import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * Created by Sebastian Agius on 13/09/2017.
 */
interface IRegion : IOperation {
    fun readChunk(dis: DataInputStream)
    fun writeChunk(dos: DataOutputStream)
}
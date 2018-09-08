package com.theultimatepleb.minecraft.server.world.io

import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * Created by Sebastian Agius on 14/09/2017.
 */
interface IPlayerData : IOperation {
    fun readCustomData(dis: DataInputStream)

    fun writeCustomData(dos: DataOutputStream)
}
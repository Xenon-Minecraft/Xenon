package com.theultimatepleb.minecraft.server.world.io.impl

import com.theultimatepleb.minecraft.crystal.GameMode
import com.theultimatepleb.minecraft.server.world.io.IPlayerData
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File

/**
 * Created by Sebastian Agius on 14/09/2017.
 */
class IOPlayerData(val file: File, var version: Int = 0, var onGround: Boolean,
                   var gameMode: GameMode, var fireTicks:Short, var healthLevel:Short,
                   var foodLevel: Short, var dimension: Short, var xpLevel: Int,
                   var xpTotal: Int, var fallDistance: Float) : IPlayerData {
    override fun read() {
        TODO("not implemented")
    }

    override fun write() {
        TODO("not implemented")
    }

    override fun readCustomData(dis: DataInputStream) {
        TODO("not implemented")
    }

    override fun writeCustomData(dos: DataOutputStream) {
        TODO("not implemented")
    }
}
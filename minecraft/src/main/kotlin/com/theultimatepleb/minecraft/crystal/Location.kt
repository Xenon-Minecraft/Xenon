package com.theultimatepleb.minecraft.crystal

import com.theultimatepleb.minecraft.crystal.block.Block
import com.theultimatepleb.minecraft.crystal.world.Chunk
import com.theultimatepleb.minecraft.crystal.world.World
import com.theultimatepleb.minecraft.server.MinecraftServer

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
data class Location(val world: World? = null,
                    val x: Double,
                    val y: Double,
                    val z: Double,
                    val pitch: Float = 0.0F,
                    val yaw: Float = 0.0F) : Cloneable {
    val blockX = Math.floor(x).toInt()
    val blockY = Math.floor(y).toInt()
    val blockZ = Math.floor(z).toInt()
    val chunk: Chunk?
        get() {
            if(world == null)
                return null
            return world.getChunkAt(this)
        }
    val block: Block?
        get() {
            if(world == null)
                return null
            return world.getBlockAt(this)
        }

    fun serialize() = MinecraftServer.GSON.toJson(this)

    companion object {
        @JvmStatic
        fun deserialize(json: String) = MinecraftServer.GSON.fromJson(json, Location::class.java)
    }
}
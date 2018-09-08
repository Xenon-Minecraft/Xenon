package com.theultimatepleb.minecraft.crystal.block

import com.theultimatepleb.minecraft.crystal.Material

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
open class BlockState(val block: Block) {
    val x = block.x
    val y = block.y
    val z = block.z
    val chunk = block.chunk
    val world = block.world
    val location = block.location
    val type = block.type

    fun update(): Boolean {
        return update(false)
    }

    fun update(force: Boolean): Boolean {
        return update(force, true)
    }

    fun update(force: Boolean, applyPhysics: Boolean): Boolean {
        TODO("Not implemented")
    }
}
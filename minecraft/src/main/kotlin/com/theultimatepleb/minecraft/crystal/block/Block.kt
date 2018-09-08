package com.theultimatepleb.minecraft.crystal.block

import com.theultimatepleb.minecraft.crystal.Location
import com.theultimatepleb.minecraft.crystal.Material
import com.theultimatepleb.minecraft.crystal.world.Biome


/**
 * Created by Sebastian Agius on 11/09/2017.
 */
data class Block(val location: Location, var type: Material, var biome: Biome, val state: BlockState) {
    val x = location.blockX
    val y = location.blockY
    val z = location.blockZ
    val chunk = location.chunk
    val world = location.world
}
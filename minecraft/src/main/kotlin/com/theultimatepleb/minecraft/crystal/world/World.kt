package com.theultimatepleb.minecraft.crystal.world

import com.theultimatepleb.minecraft.crystal.Location
import com.theultimatepleb.minecraft.crystal.block.Block

/**
 * Created by Sebastian Agius on 6/09/2017.
 */
class World(val gameRules: Map<GameRule, GameRule.Value>) {

    fun getChunkAt(location: Location): Chunk {
        TODO("Not implemented")
    }

    fun getBlockAt(location: Location): Block {
        TODO("Not implemented")
    }
}
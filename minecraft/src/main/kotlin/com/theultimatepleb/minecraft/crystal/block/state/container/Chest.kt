package com.theultimatepleb.minecraft.crystal.block.state.container

import com.theultimatepleb.minecraft.crystal.block.Block
import com.theultimatepleb.minecraft.crystal.block.state.Container
import com.theultimatepleb.minecraft.crystal.inventory.Inventory

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
class Chest(block: Block) : Container(block) {
    override fun getInventory(): Inventory {
        TODO("not implemented")
    }
}
package com.theultimatepleb.minecraft.crystal.block.state

import com.theultimatepleb.minecraft.crystal.block.Block
import com.theultimatepleb.minecraft.crystal.block.BlockState
import com.theultimatepleb.minecraft.crystal.block.Lockable
import com.theultimatepleb.minecraft.crystal.inventory.Inventory
import com.theultimatepleb.minecraft.crystal.inventory.InventoryHolder

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
abstract class Container(block: Block) : BlockState(block), InventoryHolder, Lockable {
    override fun getLock(): String {
        TODO("Not implemented")
    }

    override fun isLocked(): Boolean {
        TODO("not implemented")
    }

    override fun setLock(lock: String) {
        TODO("not implemented")
    }
}
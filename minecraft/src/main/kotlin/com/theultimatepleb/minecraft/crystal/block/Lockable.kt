package com.theultimatepleb.minecraft.crystal.block

/**
 * Created by Sebastian Agius on 11/09/2017.
 */
interface Lockable {
    fun isLocked(): Boolean

    fun setLock(lock: String)

    fun getLock(): String
}
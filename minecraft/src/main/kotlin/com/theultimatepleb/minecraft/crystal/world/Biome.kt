package com.theultimatepleb.minecraft.crystal.world

/**
 * Created by Sebastian Agius on 6/09/2017.
 */
enum class Biome(val id: Byte, val link: Biome? = null) {
    OCEAN(0), PLAINS(1), DESERT(2), EXTREME_HILLS(3), FOREST(4), TAIGA(5), SWAMPLAND(6), RIVER(7), HELL(8), NETHER(8, HELL),
    SKY(9), THE_END(9, SKY), FROZEN_OCEAN(10), FROZEN_RIVER(11), ICE_PLAINS(12), ICE_MOUNTAINS(13), MUSHROOM_ISLAND(14),
    MUSHROOM_ISLAND_SHORE(15), BEACH(16), DESERT_HILLS(17), FOREST_HILLS(18), TAIGA_HULLS(19), EXTREME_HILLS_EDGE(20),
    JUNGLE(21), JUNGLE_HILLS(22)
}
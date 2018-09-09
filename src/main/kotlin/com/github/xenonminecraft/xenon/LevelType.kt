package com.github.xenonminecraft.xenon

enum class LevelType(val id: String) {
    DEFAULT("default"),
    FLAT("flat"),
    AMPLIFIED("amplified"),
    LARGE_BIOMES("largeBiomes"),
    DEFAULT_1_1("default_1_1");

    companion object {
        fun getById(id: String): LevelType = LevelType.values().first { it.id == id}
    }
}
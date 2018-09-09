package com.github.xenonminecraft.xenon

enum class Dimension(val id: Int) {
    NETHER(-1),
    OVERWORLD(0),
    END(1);

    companion object {
        fun getById(id: Int): Dimension = Dimension.values().first { it.id == id}
    }
}
package com.github.xenonminecraft.xenon

enum class Difficulty(val id: Int) {
    PEACEFUL(0),
    EASY(1),
    MEDIUM(2),
    HARD(3);

    companion object {
        fun getById(id: Int): Difficulty = Difficulty.values().first { it.id == id}
    }
}
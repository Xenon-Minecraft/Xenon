package com.github.xenonminecraft.xenon

enum class GameMode(val id: Int) {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3);

    companion object {
        fun getById(id: Int): GameMode = GameMode.values().first { it.id == id}
    }
}
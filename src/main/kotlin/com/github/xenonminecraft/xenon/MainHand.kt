package com.github.xenonminecraft.xenon

enum class MainHand(val id: Int) {
    LEFT(0),
    RIGHT(1);

    companion object {
        fun getById(id: Int): MainHand = MainHand.values().first { it.id == id}
    }
}
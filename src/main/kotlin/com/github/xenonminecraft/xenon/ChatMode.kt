package com.github.xenonminecraft.xenon

enum class ChatMode(val id: Int) {
    ENABLED(0),
    COMMANDS_ONLY(1),
    HIDDEN(2);

    companion object {
        fun getById(id: Int): ChatMode = ChatMode.values().first { it.id == id}
    }
}
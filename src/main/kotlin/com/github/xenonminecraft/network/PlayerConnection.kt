package com.github.xenonminecraft.network

import java.net.InetSocketAddress

class PlayerConnection(val addr: InetSocketAddress) {
    var state: State = State.NONE
    var compressionThreshold: Int = -1
    var protocolVersion: Int = 0

    enum class State(val id: Int) {
        NONE(0),
        LOGIN(2),
        PLAY(0),
        STATUS(1);

        companion object {
            fun getById(id: Int): State = State.values().first { it.id == id}
        }
    }
}
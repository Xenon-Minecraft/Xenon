package com.github.xenonminecraft.network

import java.net.InetSocketAddress

class PlayerConnection(val addr: InetSocketAddress) {
    var state: State = State.NONE
    var compressionThreshold: Int = -1

    enum class State {
        NONE,
        LOGIN,
        PLAY,
        STATUS
    }
}
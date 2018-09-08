package com.theultimatepleb.minecraft.server.network.netty

import java.net.InetSocketAddress


/**
 * Created by Sebastian Agius on 13/08/2017.
 */
class NetworkManager {
    val states = hashMapOf<InetSocketAddress, State>()

    enum class State {
        NONE, STATUS, LOGIN, PLAY
    }
}
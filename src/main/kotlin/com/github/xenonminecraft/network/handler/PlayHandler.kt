package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.client.play.PacketClientSettings

class PlayHandler(val pc: PlayerConnection) {

    fun handle(p: Packet) {
        if(pc.loginCompleted) {
            //game logic packet handling
            return
        }

        when(p) {
            is PacketClientSettings -> {
                println(p.locale)
            }
        }
    }
}
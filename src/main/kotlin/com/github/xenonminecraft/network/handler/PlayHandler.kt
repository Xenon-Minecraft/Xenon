package com.github.xenonminecraft.network.handler

import com.github.xenonminecraft.network.PlayerConnection
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.client.play.PacketClientPlayerPositionAndLook
import com.github.xenonminecraft.network.packet.client.play.PacketClientSettings
import com.github.xenonminecraft.network.packet.client.play.PacketClientTeleportConfirm

class PlayHandler(val pc: PlayerConnection) {

    fun handle(p: Packet) {

        when(p) {
            is PacketClientTeleportConfirm -> {
                println("Confirmed: ${pc.confirmTeleport(p.teleportId!!)}")
            }
            is PacketClientPlayerPositionAndLook -> {
                println("Moving")
            }
        }

        if(!pc.loginCompleted) {
            when(p) {
                is PacketClientSettings -> {
                    pc.locale = p.locale!!
                    pc.mainHand = p.mainHand!!
                    pc.skinDetails = p.skinParts!!
                    pc.chatMode = p.chatMode!!
                    pc.chatColors = p.chatColors!!
                    pc.viewDistance = p.viewDistance!!

                    pc.teleport(0.0,0.0,0.0,0F,0F)
                }
            }
            return
        }


    }
}
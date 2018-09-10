/*
 * MIT License
 *
 * Copyright (c) 2018 Xenon and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
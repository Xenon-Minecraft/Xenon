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
import com.github.xenonminecraft.network.data.ServerListResponse
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.client.status.PacketClientPing
import com.github.xenonminecraft.network.packet.client.status.PacketClientRequest
import com.github.xenonminecraft.network.packet.server.status.PacketServerPong
import com.github.xenonminecraft.network.packet.server.status.PacketServerResponse
import com.google.gson.JsonObject

class StatusHandler(val pc: PlayerConnection) {
    fun handle(p: Packet) {
        when (p) {
            is PacketClientRequest -> {
                val desc = JsonObject().apply {
                    addProperty("text", "Test")
                }
                pc.sendPacket(PacketServerResponse(ServerListResponse(ServerListResponse.Version("1.13", 401), ServerListResponse.Players(20, 0, null), desc)))
            }
            is PacketClientPing -> {
                pc.sendPacket(PacketServerPong(p.payload!!))
            }
        }
    }
}
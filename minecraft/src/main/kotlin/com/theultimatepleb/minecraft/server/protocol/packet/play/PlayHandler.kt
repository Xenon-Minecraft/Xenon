package com.theultimatepleb.minecraft.server.protocol.packet.play

import com.theultimatepleb.minecraft.server.chat.ChatComponent
import com.theultimatepleb.minecraft.server.network.handler.IPlayHandler
import com.theultimatepleb.minecraft.server.network.netty.ConnectionHandler

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
class PlayHandler(val cH: ConnectionHandler) : IPlayHandler {
    override fun onDisconnect(text: ChatComponent) {
        TODO("not implemented")
    }
}
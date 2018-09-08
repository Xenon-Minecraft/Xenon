package com.theultimatepleb.minecraft.server.network.handler

import com.theultimatepleb.minecraft.server.chat.ChatComponent

/**
 * Created by Sebastian Agius on 12/09/2017.
 */
interface IHandler {
    fun onDisconnect(text: ChatComponent)
}
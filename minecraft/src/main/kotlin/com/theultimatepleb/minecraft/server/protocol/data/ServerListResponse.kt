package com.theultimatepleb.minecraft.server.protocol.data

import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.chat.ChatComponent
import java.util.*

/**
 * Created by Sebastian Agius on 10/09/2017.
 */
data class ServerListResponse(var version: Version,
                              var players: Players,
                              var description: ChatComponent,
                              var base64Favicon: String? = null) {
    class Sample(val uuid: String? = null, val name: String? = null)
    class Version(val name: String) {
        private val protocol = MinecraftServer.PROTOCOL
    }
    class Players(val max: Int, val online: Int, val sample: Array<Sample>? = null)

}
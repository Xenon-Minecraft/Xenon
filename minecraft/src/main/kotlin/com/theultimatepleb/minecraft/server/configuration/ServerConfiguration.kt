package com.theultimatepleb.minecraft.server.configuration

/**
 * Created by Sebastian Agius on 14/08/2017.
 */
class ServerConfiguration {

    val server = Server()
    val plugins = Plugins()
    val worlds = Worlds()

    inner class Server {
        val host = ""
        val port = 0

        val serverlist = ServerList()

        inner class ServerList {
            val motd = ""
            val players = arrayOf<String>()
            val max = 0
        }
    }

    inner class Worlds {
        val path = ""
    }

    inner class Plugins {
        val bukkit = true
        val sponge = true
        val path = ""
        val data = ""
    }

}
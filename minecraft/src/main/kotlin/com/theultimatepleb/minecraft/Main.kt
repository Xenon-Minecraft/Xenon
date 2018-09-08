package com.theultimatepleb.minecraft

import com.moandjiezana.toml.Toml
import com.theultimatepleb.minecraft.server.MinecraftServer
import com.theultimatepleb.minecraft.server.configuration.ServerConfiguration

/**
 * Created by Sebastian Agius on 13/08/2017.
 */
class Main {
    companion object {

        private val toml = Toml()

        @JvmStatic
        fun main(args: Array<String>) {
            MinecraftServer(
                    toml.read(Main::class.java.classLoader.getResourceAsStream("server.toml")).to(ServerConfiguration::class.java))
                    .start()
        }
    }
}
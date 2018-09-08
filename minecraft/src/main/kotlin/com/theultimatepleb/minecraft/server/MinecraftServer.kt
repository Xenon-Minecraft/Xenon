package com.theultimatepleb.minecraft.server

import com.google.gson.GsonBuilder
import com.theultimatepleb.minecraft.server.configuration.ServerConfiguration
import com.theultimatepleb.minecraft.server.network.netty.encryption.EncryptionManager
import mu.KotlinLogging
import java.io.File

/**
 * Created by Sebastian Agius on 13/08/2017.
 */
data class MinecraftServer(val serverConfiguration: ServerConfiguration) {
    companion object {
        val PROTOCOL = 338
        val NAME = "Crystal"
        val VERSION = "1.0.0"
        val PLUGIN_PLACEHOLDER = "%plugin%"
        val LOGGER = KotlinLogging.logger{}
        val GSON = GsonBuilder().create()

        var instance: MinecraftServer? = null
        set(i) {
            if(instance == null)
                field = i
            else
                throw UnsupportedOperationException("The MinecraftServer instance cannot be set once it is set once.")
        }
    }

    val worldDir: File = File(serverConfiguration.worlds.path)
    val pluginDir: File = File(serverConfiguration.plugins.path)
    val encryptionManager = EncryptionManager()

    init {

        if(!serverConfiguration.plugins.data.contains(PLUGIN_PLACEHOLDER)) {
            LOGGER.error("The location of plugin data must contain $PLUGIN_PLACEHOLDER so files do not overlap")
            System.exit(1)
        }

        worldDir.mkdirs()
        pluginDir.mkdirs()
    }

    fun start() {
        doAndTime("Generating Private and Public Key for Encryption!",
                Runnable { encryptionManager.generateKeyPair() },
                "Finished Generating Key Pair (Took %dms)")
        Thread {
            Bootstrap(serverConfiguration).start()
        }.start()

        instance = this
    }

    fun doAndTime(start: String, runnable: Runnable, finished: String) {
        LOGGER.info(start)
        val time = System.currentTimeMillis()
        runnable.run()
        LOGGER.info(finished.format(System.currentTimeMillis() - time))
    }

}
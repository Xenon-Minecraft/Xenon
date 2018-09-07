package com.github.xenonminecraft

import com.github.xenonminecraft.network.util.EncryptionManager
import com.google.gson.GsonBuilder
import mu.KotlinLogging


class Xenon {
    companion object {
        val LOGGER = KotlinLogging.logger{}
        val GSON = GsonBuilder().create()

        var instance: Xenon? = null
            set(i) {
                if(instance == null)
                    field = i
                else
                    throw UnsupportedOperationException("The MinecraftServer instance cannot be set once it is set once.")
            }

        fun doAndTime(start: String, runnable: Runnable, finished: String) {
            LOGGER.info(start)
            val time = System.currentTimeMillis()
            runnable.run()
            LOGGER.info(finished.format(System.currentTimeMillis() - time))
        }
    }

    fun start() {
        val encryptionManager = EncryptionManager()

        doAndTime("Generated Encryption Key Pair", Runnable {
            encryptionManager.generateKeyPair()
        }, "Successfully Generated Key Pair in %dms")
    }
}


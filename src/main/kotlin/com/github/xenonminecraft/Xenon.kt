package com.github.xenonminecraft

import com.github.xenonminecraft.network.auth.SessionManager
import com.github.xenonminecraft.network.util.EncryptionManager
import com.google.gson.GsonBuilder
import com.moandjiezana.toml.Toml
import mu.KotlinLogging


class Xenon(val config: Toml) {
    companion object {
        val PROTOCOL = 401

        val LOGGER = KotlinLogging.logger{}
        val GSON = GsonBuilder().create()

        var instance: Xenon? = null
            set(i) {
                if(instance == null)
                    field = i
                else
                    throw UnsupportedOperationException("The Xenon instance cannot be set once it is set once.")
            }

        fun doAndTime(start: String, runnable: Runnable, finished: String) {
            LOGGER.info(start)
            val time = System.currentTimeMillis()
            runnable.run()
            LOGGER.info(finished.format(System.currentTimeMillis() - time))
        }
    }

    val encryptionManager = EncryptionManager()
    var sessionManager = SessionManager()

    fun start() {
        instance = this

        doAndTime("Generated Encryption Key Pair", Runnable {
            encryptionManager.generateKeyPair()
        }, "Successfully Generated Key Pair in %dms")
    }
}


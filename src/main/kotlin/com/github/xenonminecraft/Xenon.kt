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

package com.github.xenonminecraft

import com.github.xenonminecraft.xenon.Difficulty
import com.github.xenonminecraft.network.auth.SessionManager
import com.github.xenonminecraft.network.util.EncryptionManager
import com.google.gson.GsonBuilder
import com.moandjiezana.toml.Toml
import mu.KotlinLogging
import java.util.*


class Xenon(val config: Toml) {
    companion object {
        val PROTOCOL = 401

        val RANDOM = Random()
        val LOGGER = KotlinLogging.logger{}
        val GSON = GsonBuilder().create()
        val DEBUG = true

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

    var difficulty = Difficulty.PEACEFUL

    fun start() {
        instance = this

        doAndTime("Generated Encryption Key Pair", Runnable {
            encryptionManager.generateKeyPair()
        }, "Successfully Generated Key Pair in %dms")
    }
}


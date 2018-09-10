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

package com.github.xenonminecraft.network.auth

import com.github.kittinunf.fuel.httpGet
import com.github.xenonminecraft.Xenon
import com.google.gson.JsonParser

class SessionManager {

    companion object {
        val SESSION_API_ENDPOINT = "https://sessionserver.mojang.com/session/minecraft/"
    }

    fun authenticate(username: String, ip: String, serverHash: String): GameProfile? {
        val (_, _, result) = SESSION_API_ENDPOINT.plus("hasJoined")
                .httpGet(listOf("username" to username, "serverId" to serverHash, "ip" to ip))
                .responseString()
        val (string, error) = result
        if (error != null)
            return null

        val obj = JsonParser().parse(string).asJsonObject
        val id = obj.get("id").asString
        obj.addProperty("id", addDashes(id))

        return Xenon.GSON.fromJson(obj, GameProfile::class.java)
    }

    private fun addDashes(id: String): String =
            StringBuilder(id).insert(8, '-').insert(13, '-').insert(18, '-').insert(23, '-').toString()
}
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
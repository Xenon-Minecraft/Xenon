package com.github.xenonminecraft.network.data

import com.google.gson.JsonObject
import java.util.*

data class ServerListResponse(val version: Version, val players: Players, val description: JsonObject, val favicon: String? = null) {

    data class Version(val name: String, val protocol: Int)
    data class Players(val max: Int, val online: Int, val sample: Array<Sample>?) {
        data class Sample(val name: String, val id: String)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Players

            if (max != other.max) return false
            if (online != other.online) return false
            if (!Arrays.equals(sample, other.sample)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = max
            result = 31 * result + online
            result = 31 * result + Arrays.hashCode(sample)
            return result
        }
    }
}
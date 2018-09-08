package com.github.xenonminecraft.network.auth

import com.google.gson.annotations.SerializedName
import java.util.*

data class GameProfile(val name: String, @SerializedName("id") val uuid: String? = null, val properties: Array<Property>? = null) {
    data class Property(val name: String, val value:String, val signature:String)

    /**
     * Generated by IntelliJ
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameProfile

        if (name != other.name) return false
        if (uuid != other.uuid) return false
        if (!Arrays.equals(properties, other.properties)) return false

        return true
    }

    /**
     * Generated by IntelliJ
     */
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + uuid!!.hashCode()
        result = 31 * result + Arrays.hashCode(properties)
        return result
    }
}
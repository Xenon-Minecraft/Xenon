package com.theultimatepleb.minecraft.server.chat

import com.google.gson.annotations.SerializedName

/**
 * Created by Sebastian Agius on 10/09/2017.
 */
data class HoverEvent(@SerializedName("show_text") val showText: String? = null,
                      @SerializedName("show_item") val showItem: String? = null,
                      @SerializedName("show_entity") val showEntity: String? = null)
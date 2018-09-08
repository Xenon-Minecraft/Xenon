package com.theultimatepleb.minecraft.server.chat

import com.google.gson.annotations.SerializedName

/**
 * Created by Sebastian Agius on 10/09/2017.
 */
data class ClickEvent(@SerializedName("open_url") val openLink: String? = null,
                      @SerializedName("run_command") val runCommand: String? = null,
                      @SerializedName("suggest_command") val suggestCommand: String? = null,
                      @SerializedName("change_page") val changePage: Int? = null)
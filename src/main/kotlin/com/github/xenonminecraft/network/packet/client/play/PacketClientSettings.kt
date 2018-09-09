package com.github.xenonminecraft.network.packet.client.play

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.readString
import com.github.xenonminecraft.network.util.readVarInt
import com.github.xenonminecraft.xenon.ChatMode
import com.github.xenonminecraft.xenon.MainHand
import com.github.xenonminecraft.xenon.SkinDetails
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Client_Settings
 *
 * Locale - String
 * View Distance - Byte
 * ChatMode - VarInt Enum
 * ChatColors - Boolean
 * Displayed Skin Parts - Unsigned Byte
 *      Cape         - 0x01
 *      Jacket       - 0x02
 *      Left Sleeve  - 0x04
 *      Right Sleeve - 0x08
 *      Left Pants   - 0x10
 *      Right Pants  - 0x20
 *      Hat          - 0x40
 * Main Hand - VarInt Enum
 */
@PacketInfo(0x04)
class PacketClientSettings(var locale: String? = null,
                           var viewDistance: Int? = null,
                           var chatMode: ChatMode? = null,
                           var chatColors: Boolean? = null,
                           var skinParts: SkinDetails? = null,
                           var mainHand: MainHand? = null) : Packet() {
    override fun decode(buf: ByteBuf) {
        locale = buf.readString()
        viewDistance = buf.readByte().toInt()
        chatMode = ChatMode.getById(buf.readVarInt())
        chatColors = buf.readBoolean()
        val skin = buf.readByte().toInt()
        skinParts = SkinDetails(skin and 0x01 == 0x01,
                skin and 0x02 == 0x02,
                skin and 0x04 == 0x04,
                skin and 0x08 == 0x08,
                skin and 0x10 == 0x10,
                skin and 0x20 == 0x20,
                skin and 0x40 == 0x40)
        mainHand = MainHand.getById(buf.readVarInt())

    }
}
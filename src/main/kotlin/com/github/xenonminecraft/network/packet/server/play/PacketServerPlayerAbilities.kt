package com.github.xenonminecraft.network.packet.server.play

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Player_Abilities_.28clientbound.29
 *
 *  Flags - Byte
 *      0x01 - Invulnerable
 *      0x02 - Flying
 *      0x04 - Allow Flying
 *      0x08 - Creative Mode (Instant Break)
 *  Flying Speed - Float
 *  Field of View Modifier - Float
 */
@PacketInfo(0x2E)
class PacketServerPlayerAbilities(var invulnerable: Boolean,
                                  var flying: Boolean,
                                  var allowFlying: Boolean,
                                  var creativeMode: Boolean,
                                  var flyingSpeed: Float,
                                  var fieldOfView: Float) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        var flags = 0x00
        if(invulnerable) flags = flags or 0x01
        if(flying) flags = flags or 0x02
        if(allowFlying) flags = flags or 0x04
        if(creativeMode) flags = flags or 0x08

        buf.writeByte(flags)
        buf.writeFloat(flyingSpeed)
        buf.writeFloat(fieldOfView)

        return buf
    }
}
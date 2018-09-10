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
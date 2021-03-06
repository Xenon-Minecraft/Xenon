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
import com.github.xenonminecraft.network.util.writeDoubles
import com.github.xenonminecraft.network.util.writeFloats
import com.github.xenonminecraft.network.util.writeVarInt
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Player_Position_And_Look_.28clientbound.29
 *
 * X - Double
 * Y - Double
 * Z - Double
 * Yaw - Float
 * Pitch - Float
 * Flags - Byte
 *      It's a bitfield, X/Y/Z/Y_ROT/X_ROT. If X is set, the x value is relative and not absolute.
 *      X     - 0x01
 *      Y     - 0x02
 *      Z     - 0x04
 *      Yaw   - 0x08  (X_ROT)
 *      Pitch - 0x10  (Y_ROT)
 * Teleport ID - VarInt (should be same in teleport confirm)
 */
@PacketInfo(0x32)
class PacketServerPlayerPositionAndLook(var x: Double,
                                        var y: Double,
                                        var z: Double,
                                        var yaw: Float,
                                        var pitch: Float,
                                        var flags: Int,
                                        var teleportId: Int) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeDoubles(x, y, z)
        buf.writeFloats(yaw, pitch)
        buf.writeByte(flags)
        buf.writeVarInt(teleportId)

        return buf
    }

    fun setRelativeFlags(x: Boolean = false, y: Boolean = false, z: Boolean = false,
                         pitch: Boolean = false, yaw: Boolean = false) {
        if(x) flags = flags or 0x01
        if(y) flags = flags or 0x02
        if(z) flags = flags or 0x04
        if(pitch) flags = flags or 0x08
        if(yaw) flags = flags or 0x10
    }
}
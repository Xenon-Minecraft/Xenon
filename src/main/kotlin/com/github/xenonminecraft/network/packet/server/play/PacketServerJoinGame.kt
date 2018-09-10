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

import com.github.xenonminecraft.Xenon
import com.github.xenonminecraft.xenon.Difficulty
import com.github.xenonminecraft.xenon.Dimension
import com.github.xenonminecraft.xenon.GameMode
import com.github.xenonminecraft.xenon.LevelType
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.util.writeString
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Specification: https://wiki.vg/Protocol#Join_Game
 *
 * Entity ID - Int
 * GameMode - Unsigned Byte
 * Dimenion - Int Enum
 * Difficulty - Unsigned Byte
 * MaxPlayers - Unsigned Byte (ignored by client)
 * LevelType - String Enum
 * Reduced Debug Info - Boolean
 */
@PacketInfo(0x25)
class PacketServerJoinGame(var entityId: Int,
                           var gameMode: GameMode,
                           var dimension: Dimension,
                           var difficulty: Difficulty,
                           var maxPlayers: Int = 0,
                           var levelType: LevelType,
                           var reducedDebugInfo: Boolean = !Xenon.DEBUG) : Packet() {
    override fun encode(): ByteBuf {
        val buf = Unpooled.buffer()
        buf.writeInt(entityId)
        buf.writeByte(gameMode.id)
        buf.writeInt(dimension.id)
        buf.writeByte(difficulty.id)
        buf.writeByte(maxPlayers)
        buf.writeString(levelType.id)
        buf.writeBoolean(reducedDebugInfo)

        return buf
    }
}
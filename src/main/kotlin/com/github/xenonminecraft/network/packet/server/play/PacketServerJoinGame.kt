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
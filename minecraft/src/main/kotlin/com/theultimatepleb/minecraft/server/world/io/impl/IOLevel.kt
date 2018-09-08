package com.theultimatepleb.minecraft.server.world.io.impl

import com.theultimatepleb.minecraft.crystal.world.GameRule
import com.theultimatepleb.minecraft.server.world.io.DataType
import com.theultimatepleb.minecraft.server.world.io.ILevel
import com.theultimatepleb.minecraft.server.world.io.StreamUtil
import java.io.*

/**
 * Created by Sebastian Agius on 13/09/2017.
 */
class IOLevel(val file: File, var version: Int = 0, val gameRules: MutableMap<GameRule, GameRule.Value> = mutableMapOf(),
              var gameType: Int = 0, var difficulty: Int = 2, var hardcore: Boolean = false,
              var structures: Boolean = false,
              var thunder: Boolean = false, var raining: Boolean = false, var seed: Long = 0.toLong(),
              var time: Long = 0.toLong(),
              var rainTime: Int = 0, var thunderTime: Int = 0, var spawnX: Int = 0, var spawnY: Int = 0,
              var spawnZ: Int = 0,
              var borderSize: Int = 60000000, var borderCenterX: Int = 0, var borderCenterY: Int = 0,
              var name: String = "world") : ILevel, StreamUtil() {

    override fun read() {
        val din = DataInputStream(FileInputStream(file))
        version = din.readByte().toInt()

        readGameRules(din)

        gameType = din.readByte().toInt()
        difficulty = din.readByte().toInt()

        hardcore = din.readBoolean()
        structures = din.readBoolean()
        raining = din.readBoolean()
        thunder = din.readBoolean()

        seed = din.readLong()
        time = din.readLong()

        rainTime = din.readInt()
        thunderTime = din.readInt()
        spawnX = din.readInt()
        spawnY = din.readInt()
        spawnZ = din.readInt()
        borderSize = din.readInt()
        borderCenterX = din.readInt()
        borderCenterY = din.readInt()

        name = din.readUTF()
    }

    override fun readGameRules(dis: DataInputStream) {
        val gdis = DataInputStream(ByteArrayInputStream(dis.readByteArray()))
        gameRules.map { 
            GameRule.ANNOUNCE_ADVANCEMENTS to GameRule.Value(gdis.readBoolean())
            GameRule.COMMAND_BLOCK_OUTPUT to GameRule.Value(gdis.readBoolean())
            GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK to GameRule.Value(gdis.readBoolean())
            GameRule.DO_DAYLIGHT_CYCLE to GameRule.Value(gdis.readBoolean())
            GameRule.DO_ENTITY_DROPS to GameRule.Value(gdis.readBoolean())
            GameRule.DO_FIRE_TICK to GameRule.Value(gdis.readBoolean())
            GameRule.DO_LIMITED_CRAFTING to GameRule.Value(gdis.readBoolean())
            GameRule.DO_MOB_LOOT to GameRule.Value(gdis.readBoolean())
            GameRule.DO_MOB_SPAWNING to GameRule.Value(gdis.readBoolean())
            GameRule.DO_TILE_DROPS to GameRule.Value(gdis.readBoolean())
            GameRule.DO_WEATHER_CYCLE to GameRule.Value(gdis.readBoolean())
            GameRule.KEEP_INVENTORY to GameRule.Value(gdis.readBoolean())
            GameRule.MOB_GRIEFING to GameRule.Value(gdis.readBoolean())
            GameRule.NATURAL_REGENERATION to GameRule.Value(gdis.readBoolean())
            GameRule.REDUCED_DEBUG_INFO to GameRule.Value(gdis.readBoolean())
            GameRule.SEND_COMMAND_FEEDBACK to GameRule.Value(gdis.readBoolean())
            GameRule.SHOW_DEATH_MESSAGES to GameRule.Value(gdis.readBoolean())
            GameRule.SPECTATORS_GENERATE_CHUNKS to GameRule.Value(gdis.readBoolean())
            GameRule.MAX_COMMAND_CHAIN_LENGTH to GameRule.Value(gdis.readInt() to DataType.INTEGER)
            GameRule.MAX_ENTITY_CRAMMING to GameRule.Value(gdis.readInt() to DataType.INTEGER)
            GameRule.RANDOM_TICK_SPEED to GameRule.Value(gdis.readInt() to DataType.INTEGER)
            GameRule.SPAWN_RADIUS to GameRule.Value(gdis.readInt() to DataType.INTEGER)
        }
        gdis.close()
    }

    override fun write() {
        val dos = DataOutputStream(FileOutputStream(file))
        dos.writeByte(version)

        writeGameRules(dos)

        dos.writeByte(gameType)
        dos.writeByte(difficulty)

        dos.writeBoolean(hardcore)
        dos.writeBoolean(structures)
        dos.writeBoolean(raining)
        dos.writeBoolean(thunder)

        dos.writeLong(seed)
        dos.writeLong(time)

        dos.writeInt(rainTime)
        dos.writeInt(thunderTime)
        dos.writeInt(spawnX)
        dos.writeInt(spawnY)
        dos.writeInt(spawnZ)
        dos.writeInt(borderSize)
        dos.writeInt(borderCenterX)
        dos.writeInt(borderCenterY)

        dos.writeUTF(name)
    }

    override fun writeGameRules(dos: DataOutputStream) {
        val baos = ByteArrayOutputStream()
        val gdos = DataOutputStream(baos)

        gdos.writeBoolean(gameRules[GameRule.ANNOUNCE_ADVANCEMENTS]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.COMMAND_BLOCK_OUTPUT]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DISABLE_ELYTRA_MOVEMENT_CHECK]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_DAYLIGHT_CYCLE]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_ENTITY_DROPS]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_FIRE_TICK]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_LIMITED_CRAFTING]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_MOB_LOOT]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_MOB_SPAWNING]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_TILE_DROPS]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.DO_WEATHER_CYCLE]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.KEEP_INVENTORY]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.MOB_GRIEFING]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.NATURAL_REGENERATION]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.REDUCED_DEBUG_INFO]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.SEND_COMMAND_FEEDBACK]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.SHOW_DEATH_MESSAGES]!!.value as Boolean)
        gdos.writeBoolean(gameRules[GameRule.SPECTATORS_GENERATE_CHUNKS]!!.value as Boolean)
        gdos.writeInt(gameRules[GameRule.MAX_COMMAND_CHAIN_LENGTH]!!.value as Int)
        gdos.writeInt(gameRules[GameRule.MAX_ENTITY_CRAMMING]!!.value as Int)
        gdos.writeInt(gameRules[GameRule.RANDOM_TICK_SPEED]!!.value as Int)
        gdos.writeInt(gameRules[GameRule.SPAWN_RADIUS]!!.value as Int)

        dos.writeByteArray(baos)
        gdos.close()
    }

}
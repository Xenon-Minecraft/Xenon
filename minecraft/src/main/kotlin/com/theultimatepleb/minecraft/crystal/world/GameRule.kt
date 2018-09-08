package com.theultimatepleb.minecraft.crystal.world

import com.theultimatepleb.minecraft.server.world.io.DataType
import org.apache.commons.lang3.StringUtils

/**
 * Created by Sebastian Agius on 13/09/2017.
 */
enum class GameRule(val type: DataType = DataType.BOOLEAN) {
    ANNOUNCE_ADVANCEMENTS, COMMAND_BLOCK_OUTPUT, DISABLE_ELYTRA_MOVEMENT_CHECK, DO_DAYLIGHT_CYCLE, DO_ENTITY_DROPS,
    DO_FIRE_TICK, DO_LIMITED_CRAFTING, DO_MOB_LOOT, DO_MOB_SPAWNING, DO_TILE_DROPS, DO_WEATHER_CYCLE, KEEP_INVENTORY,
    MAX_COMMAND_CHAIN_LENGTH(DataType.INTEGER), MAX_ENTITY_CRAMMING(DataType.INTEGER), MOB_GRIEFING, NATURAL_REGENERATION,
    RANDOM_TICK_SPEED(DataType.INTEGER), REDUCED_DEBUG_INFO, SEND_COMMAND_FEEDBACK, SHOW_DEATH_MESSAGES, SPAWN_RADIUS(DataType.INTEGER),
    SPECTATORS_GENERATE_CHUNKS;

    fun properName(): String {
        val cArray = StringUtils.capitalize(name.replace('_', ' ')).toCharArray()
        cArray[0] = cArray[0].toLowerCase()
        return String(cArray)
    }

    class Value(var rawValue: Any? = null, val type: DataType = DataType.BOOLEAN) {
        var value: Any?
            get() = rawValue
            set(v) {
                rawValue = when(type) {
                    DataType.BOOLEAN -> v as? Boolean ?: throw IllegalArgumentException("Wrong value type!")
                    DataType.INTEGER -> v as? Int ?: throw IllegalArgumentException("Wrong value type!")
                    DataType.LONG -> v as? Long ?: throw IllegalArgumentException("Wrong value type!")
                    DataType.SHORT -> v as? Short ?: throw IllegalArgumentException("Wrong value type!")
                    DataType.BYTE -> v as? Byte ?: throw IllegalArgumentException("Wrong value type!")
                }
            }
    }
}
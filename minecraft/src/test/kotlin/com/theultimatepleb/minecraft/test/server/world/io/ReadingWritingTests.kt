package com.theultimatepleb.minecraft.test.server.world.io

import org.junit.Test
import java.io.*


/**
 * Created by Sebastian Agius on 6/09/2017.
 */
class ReadingWritingTests {

    companion object {
        val TEST_FOLDER = File("tests")

        init {
            TEST_FOLDER.mkdirs()
        }
    }

    @Test
    fun readWriteStringUTF() {
        val s = "Hello"

        val file = File(TEST_FOLDER, "string_utf")
        val dos = DataOutputStream(FileOutputStream(file))

        dos.writeUTF(s)

        dos.close()

        val dis = DataInputStream(FileInputStream(file))

        val verifyS = dis.readUTF()

        dis.close()

        verifyString(s, verifyS)

        System.out.println("(readWriteStringUTF) File size = ${file.length()} bytes & String length = ${s.length}")
    }

    @Test
    fun readWriteRawString() {
        val s = "Hello"

        val file = File(TEST_FOLDER, "string_raw")
        val dos = DataOutputStream(FileOutputStream(file))

        dos.writeRawString(s)

        dos.close()

        val dis = DataInputStream(FileInputStream(file))

        val verifyS = dis.readRawString()

        verifyString(s, verifyS)

        System.out.println("(readWriteRawString) File size = ${file.length()} bytes & String length = ${s.length}")
    }

    fun DataOutputStream.writeRawString(s: String) {
        if(s.length >= Short.MAX_VALUE)
            throw IllegalArgumentException("Length too long!")

        this.writeShort(s.length)
        s.forEach {
            this.writeByte(it.toInt())
        }
    }

    fun DataInputStream.readRawString(): String {
        val length = this.readShort()

        if(length >= Short.MAX_VALUE)
            throw IllegalArgumentException("Length too long!")

        val array = CharArray(length.toInt())

        for(i in array.indices) {
            array[i] = this.readByte().toInt().toChar()
        }

        return String(array)
    }

    @Test
    fun readWriteBytes() {
        val i = 42
        val i2 = 105

        val file = File(TEST_FOLDER, "bytes")
        val dos = DataOutputStream(FileOutputStream(file))

        dos.writeByte(i)
        dos.writeByte(i2)

        dos.close()

        val dis = DataInputStream(FileInputStream(file))
        val verifyI = dis.readByte().toInt()
        val verifyI2 = dis.readByte().toInt()
        dis.close()

        verifyInt(i, verifyI)
        verifyInt(i2, verifyI2)
        System.out.println("(readWriteBytes) File size = ${file.length()} bytes")
    }

    @Test
    fun readWriteIntegers() {
        val i = 42
        val i2 = 105

        val file = File(TEST_FOLDER, "integers")
        val dos = DataOutputStream(FileOutputStream(file))

        dos.writeInt(i)
        dos.writeInt(i2)

        dos.close()

        val dis = DataInputStream(FileInputStream(file))
        val verifyI = dis.readInt()
        val verifyI2 = dis.readInt()
        dis.close()

        verifyInt(i, verifyI)
        verifyInt(i2, verifyI2)
        System.out.println("(readWriteIntegers) File size = ${file.length()} bytes")
    }

    @Test
    fun readWriteTestLevel() {
        /* Game Rules (Booleans) */
        val announceAdvancements = true
        val commandBlockOutput = true
        val disableElytraMovementCheck = false
        val doDaylightCycle = true
        val doEntityDrops = true
        val doFireTick = true
        val doLimitedCrafting = false
        val doMobLoot = true
        val doMobSpawning = true
        val doTileDrops = true
        val doWeatherCycle = true
        val keepInventory = false
        val maxCommandChainLength = 65536
        val maxEntityCramming = 24
        val mobGriefing = true
        val naturalRegeneration = true
        val randomTickSpeed = 3
        val reducedDebugInfo = false
        val sendCommandFeedback = true
        val showDeathMessages = true
        val spawnRadius = 10
        val spectatorsGenerateChunks = true

        /* Bytes */
        val gameType = 2
        val difficulty = 2

        /* Booleans */
        val hardcore = false
        val structures = false
        val thunder = true
        val raining = true

        /* Longs */
        val seed = 1021887911919129710
        val time = 9108109017892176128

        /* Integers */
        val rainTime = 405
        val thunderTime = 238
        val x = 5009
        val y = 157
        val z = 210
        val borderSize = 60000000
        val borderCenterX = 0
        val borderCenterY = 0

        /* Strings */
        val name = "world"

        val file = File(TEST_FOLDER, "level")
        writeLevel(file, gameType, difficulty, hardcore, structures, raining, thunder, seed,
                time, rainTime, thunderTime, x, y, z, borderSize, borderCenterX, borderCenterY, name,
                announceAdvancements, commandBlockOutput, disableElytraMovementCheck, doDaylightCycle,
                doEntityDrops, doFireTick, doLimitedCrafting, doMobLoot, doMobSpawning, doTileDrops,
                doWeatherCycle, keepInventory, maxCommandChainLength, maxEntityCramming, mobGriefing,
                naturalRegeneration, randomTickSpeed, reducedDebugInfo, sendCommandFeedback, showDeathMessages, spawnRadius,
                spectatorsGenerateChunks)
        readAndVerifyLevel(file, difficulty, gameType, hardcore, structures, raining, thunder, seed,
                time, rainTime, thunderTime, x, y, z, borderSize, borderCenterX, borderCenterY, name,
                announceAdvancements, commandBlockOutput, disableElytraMovementCheck, doDaylightCycle,
                doEntityDrops, doFireTick, doLimitedCrafting, doMobLoot, doMobSpawning, doTileDrops,
                doWeatherCycle, keepInventory, maxCommandChainLength, maxEntityCramming, mobGriefing,
                naturalRegeneration, randomTickSpeed, reducedDebugInfo, sendCommandFeedback, showDeathMessages, spawnRadius,
                spectatorsGenerateChunks)
        System.out.println("(readWriteTestLevel) File size = ${file.length()} bytes")
    }

    private fun writeLevel(file: File, gameType: Int, difficulty: Int, hardcore: Boolean, structures: Boolean,
                           raining: Boolean, thunder: Boolean, seed: Long, time: Long, rainTime: Int,
                           thunderTime: Int, x: Int, y: Int, z: Int, borderSize: Int, borderCenterX: Int,
                           borderCenterY: Int, name: String,

                           announceAdvancements: Boolean, commandBlockOutput: Boolean,
                           disableElytraMovementCheck: Boolean,
                           doDaylightCycle: Boolean, doEntityDrops: Boolean, doFireTick: Boolean,
                           doLimitedCrafting: Boolean, doMobLoot: Boolean, doMobSpawning: Boolean,
                           doTileDrops: Boolean, doWeatherCycle: Boolean, keepInventory: Boolean,
                           maxCommandChainLength: Int, maxEntityCramming: Int, mobGriefing: Boolean,
                           naturalRegeneration: Boolean, randomTickSpeed: Int, reducedDebugInfo: Boolean,
                           sendCommandFeedback: Boolean, showDeathMessages: Boolean, spawnRadius: Int,
                           spectatorsGenerateChunks: Boolean) {
        val dos = DataOutputStream(FileOutputStream(file))

        val baos = ByteArrayOutputStream()
        val gameRules = DataOutputStream(baos)
        gameRules.writeBoolean(announceAdvancements)
        gameRules.writeBoolean(commandBlockOutput)
        gameRules.writeBoolean(disableElytraMovementCheck)
        gameRules.writeBoolean(doDaylightCycle)
        gameRules.writeBoolean(doEntityDrops)
        gameRules.writeBoolean(doFireTick)
        gameRules.writeBoolean(doLimitedCrafting)
        gameRules.writeBoolean(doMobLoot)
        gameRules.writeBoolean(doMobSpawning)
        gameRules.writeBoolean(doTileDrops)
        gameRules.writeBoolean(doWeatherCycle)
        gameRules.writeBoolean(keepInventory)
        gameRules.writeInt(maxCommandChainLength)
        gameRules.writeInt(maxEntityCramming)
        gameRules.writeBoolean(mobGriefing)
        gameRules.writeBoolean(naturalRegeneration)
        gameRules.writeInt(randomTickSpeed)
        gameRules.writeBoolean(reducedDebugInfo)
        gameRules.writeBoolean(sendCommandFeedback)
        gameRules.writeBoolean(showDeathMessages)
        gameRules.writeInt(spawnRadius)
        gameRules.writeBoolean(spectatorsGenerateChunks)

        val gameArray = baos.toByteArray()
        dos.writeInt(gameArray.size)
        dos.write(gameArray)
        gameRules.close()

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
        dos.writeInt(x)
        dos.writeInt(y)
        dos.writeInt(z)
        dos.writeInt(borderSize)
        dos.writeInt(borderCenterX)
        dos.writeInt(borderCenterY)

        dos.writeUTF(name)
    }

    private fun readBytes(input: InputStream, size: Int): ByteArray {
        val array = ByteArray(size)
        for(i in array.indices) {
            array[i] = input.read().toByte()
        }

        return array
    }

    private fun readAndVerifyLevel(file: File, gameType: Int, difficulty: Int, hardcore: Boolean, structures: Boolean,
                                   raining: Boolean, thunder: Boolean, seed: Long, time: Long, rainTime: Int,
                                   thunderTime: Int, x: Int, y: Int, z: Int, borderSize: Int, borderCenterX: Int,
                                   borderCenterY: Int, name: String,

                                   announceAdvancements: Boolean, commandBlockOutput: Boolean,
                                   disableElytraMovementCheck: Boolean,
                                   doDaylightCycle: Boolean, doEntityDrops: Boolean, doFireTick: Boolean,
                                   doLimitedCrafting: Boolean, doMobLoot: Boolean, doMobSpawning: Boolean,
                                   doTileDrops: Boolean, doWeatherCycle: Boolean, keepInventory: Boolean,
                                   maxCommandChainLength: Int, maxEntityCramming: Int, mobGriefing: Boolean,
                                   naturalRegeneration: Boolean, randomTickSpeed: Int, reducedDebugInfo: Boolean,
                                   sendCommandFeedback: Boolean, showDeathMessages: Boolean, spawnRadius: Int,
                                   spectatorsGenerateChunks: Boolean) {
        val din = DataInputStream(FileInputStream(file))

        val size = din.readInt()
        val array = readBytes(din, size)
        val gameDin = DataInputStream(ByteArrayInputStream(array))

        verifyBoolean(announceAdvancements, gameDin.readBoolean())
        verifyBoolean(commandBlockOutput, gameDin.readBoolean())
        verifyBoolean(disableElytraMovementCheck, gameDin.readBoolean())
        verifyBoolean(doDaylightCycle, gameDin.readBoolean())
        verifyBoolean(doEntityDrops, gameDin.readBoolean())
        verifyBoolean(doFireTick, gameDin.readBoolean())
        verifyBoolean(doLimitedCrafting, gameDin.readBoolean())
        verifyBoolean(doMobLoot, gameDin.readBoolean())
        verifyBoolean(doMobSpawning, gameDin.readBoolean())
        verifyBoolean(doTileDrops, gameDin.readBoolean())
        verifyBoolean(doWeatherCycle, gameDin.readBoolean())
        verifyBoolean(keepInventory, gameDin.readBoolean())
        verifyInt(maxCommandChainLength, gameDin.readInt())
        verifyInt(maxEntityCramming, gameDin.readInt())
        verifyBoolean(mobGriefing, gameDin.readBoolean())
        verifyBoolean(naturalRegeneration, gameDin.readBoolean())
        verifyInt(randomTickSpeed, gameDin.readInt())
        verifyBoolean(reducedDebugInfo, gameDin.readBoolean())
        verifyBoolean(sendCommandFeedback, gameDin.readBoolean())
        verifyBoolean(showDeathMessages, gameDin.readBoolean())
        verifyInt(spawnRadius, gameDin.readInt())
        verifyBoolean(spectatorsGenerateChunks, gameDin.readBoolean())

        gameDin.close()

        verifyInt(gameType, din.readByte().toInt())
        verifyInt(difficulty, din.readByte().toInt())

        verifyBoolean(hardcore, din.readBoolean())
        verifyBoolean(structures, din.readBoolean())
        verifyBoolean(raining, din.readBoolean())
        verifyBoolean(thunder, din.readBoolean())

        verifyLong(seed, din.readLong())
        verifyLong(time, din.readLong())

        verifyInt(rainTime, din.readInt())
        verifyInt(thunderTime, din.readInt())
        verifyInt(x, din.readInt())
        verifyInt(y, din.readInt())
        verifyInt(z, din.readInt())
        verifyInt(borderSize, din.readInt())
        verifyInt(borderCenterX, din.readInt())
        verifyInt(borderCenterY, din.readInt())

        verifyString(name, din.readUTF())
    }

    private fun verifyInt(i: Int, i2: Int) {
        assert(i == i2, { "Failed to verify i ($i) with i2 ($i2)" })
    }

    private fun verifyLong(i: Long, i2: Long) {
        assert(i == i2, { "Failed to verify i ($i) with i2 ($i2)" })
    }


    private fun verifyString(i: String, i2: String) {
        checkNotNull(i, { "i is null" })
        checkNotNull(i2, { "i2 is null" })
        assert(i == i2, { "Failed to verify i ($i) with i2 ($i2)" })
    }

    private fun verifyBoolean(i: Boolean, i2: Boolean) {
        assert(i == i2, { "Failed to verify i ($i) with i2 ($i2)" })
    }

}
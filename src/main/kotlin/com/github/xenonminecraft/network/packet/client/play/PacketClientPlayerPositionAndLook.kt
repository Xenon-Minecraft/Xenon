package com.github.xenonminecraft.network.packet.client.play

import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import io.netty.buffer.ByteBuf

/**
 * Specification: https://wiki.vg/Protocol#Player_Position_And_Look_.28serverbound.29
 *
 * X - Double
 * Y - Double
 * Z - Double
 * Yaw - Float
 * Pitch - Float
 * On Ground - Boolean
 */
@PacketInfo(0x11)
class PacketClientPlayerPositionAndLook(var x: Double? = null,
                                        var y: Double? = null,
                                        var z: Double? = null,
                                        var yaw: Float? = null,
                                        var pitch: Float? = null,
                                        var onGround: Boolean? = null) : Packet() {
    override fun decode(buf: ByteBuf) {
        x = buf.readDouble()
        y = buf.readDouble()
        z = buf.readDouble()

        yaw = buf.readFloat()
        pitch = buf.readFloat()

        onGround = buf.readBoolean()
    }
}
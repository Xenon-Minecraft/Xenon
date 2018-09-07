package com.github.xenonminecraft.network.packet

class PacketManager {
    companion object {
        var statusPackets: Map<Int, Class<Packet>>? = null
        var loginPackets: Map<Int, Class<Packet>>? = null
        var playPackets: Map<Int, Class<Packet>>? = null
    }
}
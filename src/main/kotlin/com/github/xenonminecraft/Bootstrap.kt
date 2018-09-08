package com.github.xenonminecraft

import com.github.xenonminecraft.network.XenonServerInitialiser
import com.github.xenonminecraft.network.packet.Packet
import com.github.xenonminecraft.network.packet.PacketInfo
import com.github.xenonminecraft.network.packet.PacketManager
import com.google.common.reflect.ClassPath
import com.moandjiezana.toml.Toml
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LoggingHandler
import java.io.File
import java.nio.file.Files

val MINECRAFT_VERSION = "1.13"
val XENON_VERSION = "0.0dev"

fun main(args: Array<String>) {
    println("Handling early init")
    PacketManager.statusPackets = loadPacketsFrom("com.github.xenonminecraft.network.packet.client.status")
    PacketManager.loginPackets = loadPacketsFrom("com.github.xenonminecraft.network.packet.client.login")
    PacketManager.playPackets = loadPacketsFrom("com.github.xenonminecraft.network.packet.client.play")
    println("Successfully loaded all packets")

    val config = loadConfig()

    val port = config.getLong("network.port").toInt()
    val ip = config.getString("network.ip")
    println("Starting Minecraft Server $MINECRAFT_VERSION with Xenon $XENON_VERSION on $ip:$port")
    Xenon(config).start()

    Thread {

        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()

        val bootstrap = ServerBootstrap()

        try {
            bootstrap.apply {
                group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel::class.java)
                        .handler(LoggingHandler())
                        .childHandler(XenonServerInitialiser())
                option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)

                Xenon.LOGGER.info("Binding on Netty")
                bind(ip, port).sync().channel().closeFuture().sync()

            }
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }.apply {
        name = "netty"
        start()
    }
}

private fun loadPacketsFrom(pack: String): Map<Int, Class<Packet>> {
    val cp = ClassPath.from(Bootstrap::class.java.classLoader)
    val mutableMap = mutableMapOf<Int, Class<Packet>>()
    cp.getTopLevelClassesRecursive(pack).forEach {
        val clazz = it.load()
        val info = clazz.getAnnotation(PacketInfo::class.java)
        if (Packet::class.java.isAssignableFrom(clazz))
            mutableMap[info.id] = clazz as Class<Packet>
    }

    return mutableMap
}

fun loadConfig(): Toml {
    val config = File("server.toml")

    if (!config.exists())
        Files.copy(Bootstrap::class.java.classLoader.getResourceAsStream("server.toml"), config.toPath())

    return Toml().read(config)
}

class Bootstrap
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



fun main(args: Array<String>) {
    println("Handling early init")
    PacketManager.statusPackets = loadPacketsFrom("com.github.xenonminecraft.network.packet.client.status")
    PacketManager.loginPackets = loadPacketsFrom("com.github.xenonminecraft.network.packet.client.login")
    PacketManager.playPackets = loadPacketsFrom("com.github.xenonminecraft.network.packet.client.play")
    println("Successfully loaded all packets")

    val config = loadConfig()

    val port = config.getLong("network.port").toInt()
    val ip = config.getString("network.ip")
    println("Starting Minecraft Server ${Bootstrap.MINECRAFT_VERSION} with Xenon ${Bootstrap.XENON_VERSION} on $ip:$port")
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

class Bootstrap {
    companion object {
        val MINECRAFT_VERSION = "1.13"
        val XENON_VERSION = "0.0dev"
    }
}
package com.theultimatepleb.minecraft.server

import com.theultimatepleb.minecraft.server.configuration.ServerConfiguration
import com.theultimatepleb.minecraft.server.network.netty.*
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import mu.KotlinLogging


/**
 * Created by Sebastian Agius on 14/08/2017.
 */
class Bootstrap(val properties: ServerConfiguration) {
    private val logger = KotlinLogging.logger {}
    val networkManager = NetworkManager()

    fun start() {
        dumpServerConfiguration()
        bootstrap()
    }

    private fun bootstrap() {
        val bossGroup = NioEventLoopGroup()
        val workerGroup = NioEventLoopGroup()

        val serverBootstrap = ServerBootstrap().apply {
            group(bossGroup, workerGroup)
            channel(NioServerSocketChannel::class.java).childHandler(object : ChannelInitializer<SocketChannel>() {
                override fun initChannel(ch: SocketChannel) {
                    networkManager.states.put(ch.remoteAddress(), NetworkManager.State.NONE)
                    ch.pipeline().addLast("splitter", Varint21FrameDecoder())
                    ch.pipeline().addLast("decoder", PacketDecoder(ch.remoteAddress(), networkManager))

                    ch.pipeline().addLast("prepender", Varint21FrameEncoder())
                    ch.pipeline().addLast("encoder", PacketEncoder(ch.remoteAddress(), networkManager))

                    ch.pipeline().addLast("packet_handler", ConnectionHandler(ch.remoteAddress(), networkManager))
                }
            })
            option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)
        }

        val future = serverBootstrap.bind(properties.server.host, properties.server.port).sync()

        future.channel().closeFuture().sync()

        workerGroup.shutdownGracefully()
        bossGroup.shutdownGracefully()

    }

    fun dumpServerConfiguration() {
        logger.debug("Will Listen on: ${properties.server.host}:${properties.server.port}")

        logger.debug("Motd: ${properties.server.serverlist.motd}")

        logger.debug("Players: ")
        properties.server.serverlist.players.forEach {
            logger.debug(it)
        }

        logger.debug("Player Max: ${properties.server.serverlist.max}")

        logger.debug("Bukkit/Sponge Enabled: ${properties.plugins.bukkit}/${properties.plugins.sponge}")
        logger.debug("Plugin Path: ${properties.plugins.path} & Plugin Data Path: ${properties.plugins.data}")
    }
}
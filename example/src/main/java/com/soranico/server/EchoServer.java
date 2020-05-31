package com.soranico.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * @title com.soranico.server.EchoServer
 * @description
 *        <pre>
 *          Netty服务端
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/5/31
 *
 * </pre>
 */
@Slf4j
public class EchoServer {

    private static final int bossThreadNum = 1;
    private static final int workerThreadNum = 4;
    private static final int businessThreadNums = 32;
    private static final int backlog = 1024;
    private static final int port = 9090;

    public static void main(String[] args) {
        /**
         * bossGroup
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossThreadNum,
                new ThreadPerTaskExecutor(new DefaultThreadFactory("boss-thread-pool")));
        /**
         * workerGroup
         */
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreadNum,
                new ThreadPerTaskExecutor(new DefaultThreadFactory("worker-thread-pool")));

        /**
         * 业务线程
         */
        EventExecutor businessExecutor = new DefaultEventExecutor(workerGroup,
                new DefaultThreadFactory("business-thread-pool"), businessThreadNums, RejectedExecutionHandlers.reject());

        /**
         * 启动类
         */
        ServerBootstrap echoServer = new ServerBootstrap();
        echoServer.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, backlog)
                .childHandler(EchoServerHandlerInitializer.INSTANCE.setBusinessExecutor(businessExecutor))
                .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(true,
                        8, 8, 8192, 11,
                        512, 256, 64,
                        true, 0));

        try {
            Channel channel = echoServer.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            businessExecutor.shutdownGracefully();
        }


    }

}

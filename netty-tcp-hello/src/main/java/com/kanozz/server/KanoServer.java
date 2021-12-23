package com.kanozz.server;

import com.kanozz.server.constant.AuthStateEnum;
import com.kanozz.server.handler.KanoServerChannelInitializer;
import com.kanozz.server.constant.KanoServerChannelKey;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.util.concurrent.Executor;

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
@SuppressWarnings("unchecked")
public class KanoServer {

    /**
     * 接收请求线程池大小
     */
    private static final int BOSS_THREAD_NUM = 1;
    /**
     * 处理I/O线程池大小
     */
    private static final int WORKER_THREAD_NUM = 4;
    /**
     * 业务线程池大小
     */
    private static final int BUSINESS_THREAD_NUMS = 32;
    /**
     * 业务线程池任务队列大小
     */
    private static final int BUSINESS_TASK_NUMS = 1024;
    /**
     * 连接等待大小
     */
    private static final int BACKLOG = 1024;
    private static final int PORT = 9090;
    private static Class clazz;
    private static Class eventLoopGroupClazz;
    private static final String WIN_OS = "Win";

    /**
     * 加载类时根据os选择ServerChannel
     * win : NioServerSocketChannel
     * linux : EpollServerSocketChannel
     */
    static {
        String os = System.getProperty("os.name");
        if (os.contains(WIN_OS)|| os.contains("Mac")) {
            clazz = NioServerSocketChannel.class;
            eventLoopGroupClazz = NioEventLoopGroup.class;
        } else {
            clazz = EpollServerSocketChannel.class;
            eventLoopGroupClazz = EpollEventLoopGroup.class;
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        /**
         * 获取构造方法
         * 两个参数,指定线程工厂
         */
        Constructor constructor = eventLoopGroupClazz.getConstructor(int.class, Executor.class);

        /**
         * bossGroup
         */
        EventLoopGroup bossGroup = (EventLoopGroup) constructor.newInstance(BOSS_THREAD_NUM,
                new ThreadPerTaskExecutor(new DefaultThreadFactory("boss-thread-pool")));



        /**
         * workerGroup
         */
        EventLoopGroup workerGroup = (EventLoopGroup) constructor.newInstance(WORKER_THREAD_NUM,
                new ThreadPerTaskExecutor(new DefaultThreadFactory("worker-thread-pool")));

        /**
         * 业务线程
         */
        EventExecutorGroup businessExecutorGroup = new DefaultEventExecutorGroup(BUSINESS_THREAD_NUMS,
                new DefaultThreadFactory("business-thread-pool"), BUSINESS_TASK_NUMS, RejectedExecutionHandlers.reject());

        /**
         * 启动类
         */
        ServerBootstrap echoServer = new ServerBootstrap();
        echoServer.group(bossGroup, workerGroup)
                .channel(clazz)
                .option(ChannelOption.SO_BACKLOG, BACKLOG)
                .childHandler(KanoServerChannelInitializer.INSTANCE.setBusinessExecutorGroup(businessExecutorGroup))
                .childAttr(KanoServerChannelKey.AUTH_KEY, AuthStateEnum.init.name())
                .childOption(ChannelOption.ALLOCATOR, new PooledByteBufAllocator(true,
                        8, 8, 8192, 11,
                        512, 256, 64,
                        true, 0));

        try {
            Channel channel = echoServer.bind(PORT).sync().channel();
            log.info("server start and listen {}", PORT);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server start failed", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            businessExecutorGroup.shutdownGracefully();
        }
    }

}

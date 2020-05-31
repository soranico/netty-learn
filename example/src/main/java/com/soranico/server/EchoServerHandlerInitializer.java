package com.soranico.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.EventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * @title com.soranico.server.EchoServerHandlerInitializer
 * @description
 *        <pre>
 *          管理处理客户端连接Handler
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/5/31
 *
 * </pre>
 */
@Slf4j
public class EchoServerHandlerInitializer extends ChannelInitializer<SocketChannel> {

    public static final EchoServerHandlerInitializer INSTANCE = new EchoServerHandlerInitializer();

    private EventExecutor businessExecutor;

    public EchoServerHandlerInitializer setBusinessExecutor(EventExecutor businessExecutor) {
        this.businessExecutor=businessExecutor;
        return this;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        log.info("accept client");
    }
}

package com.kanozz.server.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * @title com.soranico.server.handler.KanoServerChannelInitializer
 * @description
 *        <pre>
 *          服务端handler
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/5/31
 *
 * </pre>
 */
@Slf4j
public class KanoServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    public static final KanoServerChannelInitializer INSTANCE = new KanoServerChannelInitializer();

    private EventExecutorGroup businessExecutorGroup;

    public KanoServerChannelInitializer setBusinessExecutorGroup(EventExecutorGroup businessExecutorGroup) {
        this.businessExecutorGroup=businessExecutorGroup;
        return this;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(businessExecutorGroup,"dispatcherHandler",KanoServerDispatcherHandler.INSTANCE);
        pipeline.addLast("ideaHandler",new IdleStateHandler(0,0,5, TimeUnit.SECONDS));

    }
}

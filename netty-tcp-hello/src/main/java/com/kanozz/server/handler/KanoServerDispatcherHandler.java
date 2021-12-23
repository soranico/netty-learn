package com.kanozz.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <pre>
 * @title com.soranico.server.handler.KanoServerDispatcherHandler
 * @description
 *        <pre>
 *          服务端消息分发，根据消息的类型交给不同的handler解析
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/5
 *
 * </pre>
 */
@ChannelHandler.Sharable
public class KanoServerDispatcherHandler extends ChannelInboundHandlerAdapter {

    public static final KanoServerDispatcherHandler INSTANCE = new KanoServerDispatcherHandler();

    private KanoServerDispatcherHandler() {
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

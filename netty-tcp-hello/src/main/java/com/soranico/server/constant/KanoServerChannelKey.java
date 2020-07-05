package com.soranico.server.constant;

import io.netty.util.AttributeKey;

/**
 * <pre>
 * @title com.soranico.server.constant.KanoServerChannelKey
 * @description
 *        <pre>
 *          服务端channel的属性key
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/5
 *
 * </pre>
 */
public class KanoServerChannelKey {


    public static final AttributeKey<String> AUTH_KEY=AttributeKey.valueOf(AuthStateEnum.init.name());

}

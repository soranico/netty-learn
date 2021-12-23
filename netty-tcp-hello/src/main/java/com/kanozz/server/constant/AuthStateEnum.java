package com.kanozz.server.constant;

/**
 * <pre>
 * @title com.soranico.server.constant.AuthStateEunm
 * @description
 *        <pre>
 *          鉴权状态
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/5
 *
 * </pre>
 */
public enum AuthStateEnum {
    /**
     * 初始态
     */
    init,
    /**
     * 等待鉴权态
     */
    waiting,
    /**
     * 鉴权成功态
     */
    success,
    /**
     * 鉴权失败态
     */
    failed;
}

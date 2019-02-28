package com.beautyboss.slogen.config.common;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
public interface Status {

    /**
     * 是否成功状态
     *
     * @return 成功状态，返回true；否则，返回false
     */
     boolean isSuccess();

    /**
     * 状态码值
     *
     * @return 状态码值
     */
    int getStatus();

    /**
     * 错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 状态描述
     *
     * @return 状态描述
     */
    String getMsg();

    /**
     * 状态描述
     *
     * @return 状态描述
     */
    String getMsg(Object... format);

}

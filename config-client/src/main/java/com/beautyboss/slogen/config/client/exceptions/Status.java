package com.beautyboss.slogen.config.client.exceptions;

/**
 * Author : Slogen
 * Date   : 2019/2/28
 */
public interface Status {

    boolean isSuccess();

    String getCode();

    String getMsg();

    String getMsg(Object... format);

    int getStatus();

}

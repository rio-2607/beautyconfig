package com.beautyboss.slogen.config.client.exceptions;

/**
 * Author : Slogen
 * Date   : 2019/2/28
 */
public class ConfigException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private int status;

    public ConfigException() {

    }

    public ConfigException(String errorCode, String errorMessage,int status) {
        super(errorCode + ":" + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public ConfigException(StatusCode statusCode) {
        this(statusCode.getCode(),statusCode.getMsg(),statusCode.getStatus());
    }


    public ConfigException(StatusCode statusCode, Object... args) {
        this(statusCode.getCode(),statusCode.getMsg(args),statusCode.getStatus());
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Integer getStatus(){
        return this.status;
    }

}

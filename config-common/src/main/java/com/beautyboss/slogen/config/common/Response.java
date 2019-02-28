package com.beautyboss.slogen.config.common;

import com.beautyboss.slogen.config.common.enums.StatusCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Data
public class Response<C,T> implements Status {

    private String code;
    private String msg;
    private int status;
    private T data;

    public Response() {
    }

    public C success(T data) {
        this.data = data;
        this.code = StatusCode.SERVICE_RUN_SUCCESS.getCode();
        this.msg = StatusCode.SERVICE_RUN_SUCCESS.getMsg();
        this.status = StatusCode.SERVICE_RUN_SUCCESS.getStatus();
        return (C) this;
    }


    public Response(Status status) {
        this(status.getStatus(), status.getCode(), status.getMsg());
    }

    public Response(int status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    public static Response successResponse(Object data) {
        Response response = new Response();
        response.setCode(StatusCode.SERVICE_RUN_SUCCESS.getCode());
        response.setStatus(StatusCode.SERVICE_RUN_SUCCESS.getStatus());
        response.setData(data);
        response.setMsg(StatusCode.SERVICE_RUN_SUCCESS.getMsg());
        return response;
    }

    public static Response failResponse(String code,int status,String msg) {
        Response response = new Response();
        response.setData(null);
        response.setMsg(msg);
        response.setCode(code);
        response.setStatus(status);
        return response;
    }

    public static Response failResponse(StatusCode statusCode, String message) {
        Response response = new Response();
        response.setData(null);
        response.setMsg(String.format(statusCode.getMsg(), message));
        response.setCode(statusCode.getCode());
        response.setStatus(statusCode.getStatus());
        return response;
    }

    public static Response failResponse(StatusCode statusCode) {
        return failResponse(statusCode,"");
    }

    public static Response create(int status, String code, String message) {
        Response response = new Response();
        response.setStatus(status);
        response.setCode(code);
        response.setMsg(message);
        return response;
    }

    @JsonProperty("success")
    public boolean isSuccess() {
        // 有些地方0也表示成功
        return status == 10000;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public String getMsg(Object... format) {
        return null;
    }
}

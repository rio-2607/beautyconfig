package com.beautyboss.slogen.config.client.exceptions;

/**
 * Author : Slogen
 * Date   : 2019/2/28
 */
public enum StatusCode implements Status {
    SERVICE_RUN_SUCCESS(10000, "服务运行成功"),
    PARAMEMTER_VALIDATE_ILLEGAL(10001, "参数非法:%s"),
    PARAM_IS_EMPTY(10002, "参数%s不能为空"),
    DATA_NOT_EXIST(10003, "%s不存在"),
    JSON_FORMAT_ERROR(10004, "JSON格式不正确"),
    DATA_FORMAT_ERROR(10005, "数据格式化异常"),
    HTTP_ACESS_ERROR(10006, "HTTP访问异常"),
    DB_ACCESS_ERROR(80000, "数据库异常:%s"),
    SERVICE_RUN_ERROR(99999, "服务器忙,请稍后再试"),
    BUSINESS_ERROR(90000, "业务异常,%s"),

    ERROR_CONFIG_NOT_EXIST(30000,"配置不存在:key:%s");

    private int status;
    private String msg;

    StatusCode(int status, String message) {
        this.status = status;
        this.msg = message;
    }

    @Override
    public boolean isSuccess() {
        return getStatus() == 10000L;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMsg() {
        return String.format(msg, "");
    }

    @Override
    public String getMsg(Object... objects) {
        if (objects == null) {
            return getMsg();
        }

        return String.format(msg, objects);
    }
}

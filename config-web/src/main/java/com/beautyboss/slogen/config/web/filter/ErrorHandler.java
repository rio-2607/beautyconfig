package com.beautyboss.slogen.config.web.filter;

import com.beautyboss.slogen.config.common.Response;
import com.beautyboss.slogen.config.common.enums.StatusCode;
import com.beautyboss.slogen.config.common.exceptions.ConfigException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@ControllerAdvice
@ResponseBody
public class ErrorHandler {
    private final static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @ExceptionHandler(JsonMappingException.class)
    public Response handlerException(JsonMappingException e) {
        logger.error(StatusCode.JSON_FORMAT_ERROR.getCode(), e);
        Response response = Response.failResponse(StatusCode.JSON_FORMAT_ERROR);
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response handlerException(HttpMessageNotReadableException e) {
        logger.error(StatusCode.HTTP_ACESS_ERROR.getCode(), e);
        Response resp = Response.failResponse(StatusCode.HTTP_ACESS_ERROR);
        return resp;
    }

    @ExceptionHandler(JsonParseException.class)
    public Response handlerException(JsonParseException e) {
        logger.error(StatusCode.JSON_FORMAT_ERROR.getCode(), e);
        Response resp = Response.failResponse(StatusCode.JSON_FORMAT_ERROR);
        return resp;
    }

    @ExceptionHandler(InvalidFormatException.class)
    public Response handlerException(InvalidFormatException e) {
        logger.error("错误", e);
        Response resp = Response.failResponse(StatusCode.DATA_FORMAT_ERROR);
        return resp;
    }

    @ExceptionHandler(Exception.class)
    public Response handlerException(Exception e) {
        logger.error(StatusCode.SERVICE_RUN_ERROR.getCode(), e);
        return Response.failResponse(StatusCode.SERVICE_RUN_ERROR);
    }

    @ExceptionHandler(ConfigException.class)
    public Response handlerBusinessException(ConfigException e) {
        logger.error(e.getErrorCode(),e.getErrorMessage());
        return Response.failResponse(e.getErrorCode(),e.getStatus(),e.getErrorMessage());
    }
}

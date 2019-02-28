package com.beautyboss.slogen.config.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@WebFilter
@Slf4j
public class AccessLogFilter  extends OncePerRequestFilter {


    private static final int MAX_SIZE = 10240;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Long startTime = System.currentTimeMillis();
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(requestWrapper, responseWrapper);

        String requestPayload = getPayLoad(requestWrapper.getContentAsByteArray(),
                httpServletRequest.getCharacterEncoding());
        String responsePayload = getPayLoad(responseWrapper.getContentAsByteArray(),
                httpServletResponse.getCharacterEncoding());
        responseWrapper.copyBodyToResponse();
        logger.info(httpServletRequest.getRequestURI() +  " [ " + (System.currentTimeMillis() - startTime)  + " ] -> " + requestPayload + " >> " + responsePayload);
    }

    private String getPayLoad(byte[] buf, String characterEncoding) {
        String payload = "";
        if (buf == null) {
            return payload;
        }
        if (buf.length > 0) {
            int length = Math.min(buf.length, MAX_SIZE);
            try {
                payload = new String(buf, 0, length, characterEncoding);
            } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
            }
        }
        return payload;
    }
}

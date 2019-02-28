package com.beautyboss.slogen.config.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@EnableAspectJAutoProxy
@ComponentScans({@ComponentScan("com.beautyboss.slogen.config")})
@SpringBootApplication(scanBasePackages = "com.beautyboss.slogen.config")
@ServletComponentScan
public class ApplicationStarter extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = new ObjectMapper();
        //忽略无法识别字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //忽略空值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);
        converters.add(converter);
    }

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationStarter.class);
        File pidFile = new File("app.pid");
        pidFile.setWritable(true, true);
        pidFile.setExecutable(false);
        pidFile.setReadable(true);
        application.addListeners(new ApplicationPidFileWriter(pidFile));
        application.run(args);
    }
}

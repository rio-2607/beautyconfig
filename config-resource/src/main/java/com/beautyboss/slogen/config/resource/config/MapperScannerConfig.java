package com.beautyboss.slogen.config.resource.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Configuration
@AutoConfigureAfter(MybatisConfig.class)
public class MapperScannerConfig {

    @Bean
    public MapperScannerConfigurer buildMapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setBasePackage("com.beautyboss.slogen.config.resource.mapper");
        return configurer;
    }


}

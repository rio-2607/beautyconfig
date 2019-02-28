package com.beautyboss.slogen.config.web.dto;

import lombok.Data;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/24
 */
@Data
public class EnvDTO {

    private Long id;

    private String name;

    private Integer type;

    private Integer registerType;

    private List<String> ips;

}

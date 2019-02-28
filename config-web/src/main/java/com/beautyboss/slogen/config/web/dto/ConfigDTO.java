package com.beautyboss.slogen.config.web.dto;

import lombok.Data;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Data
public class ConfigDTO {

    private Long id;

    private Long projectId;

    private Long groupId;

    private String key;

    private String value;

    private int type;

    private String desc;

    private List<Integer> envIds;

}

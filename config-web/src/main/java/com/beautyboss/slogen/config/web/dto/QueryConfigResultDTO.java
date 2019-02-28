package com.beautyboss.slogen.config.web.dto;

import lombok.Data;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Data
public class QueryConfigResultDTO {

    private Long configId;

    private String key;

    private String desc;

    private Integer type;

    private Long projectId;

    private Long groupId;

    private List<QueryConfigInstanceDTO> instances;

}

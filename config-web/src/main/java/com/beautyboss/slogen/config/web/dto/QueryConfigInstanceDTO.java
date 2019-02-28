package com.beautyboss.slogen.config.web.dto;

import lombok.Data;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Data
public class QueryConfigInstanceDTO {

    private Long configId;

    private Integer envId;

    private String value;

}

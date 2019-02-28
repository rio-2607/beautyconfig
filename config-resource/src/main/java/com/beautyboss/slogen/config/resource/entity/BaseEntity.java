package com.beautyboss.slogen.config.resource.entity;

import lombok.Data;


/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class BaseEntity {

    private Long id;

    private Long createUserId = 0L;

    private Long modifyUserId = 0L;

    private Long createTime;

    private Long modifyTime;

}

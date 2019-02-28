package com.beautyboss.slogen.config.service.domain;

import lombok.Data;

import java.util.Date;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class BaseDO {

    private Long id;

    private Long createUserId = 0L;

    private Long modifyUserId = 0L;

    private Date createTime;

    private Date modifyTime;

}

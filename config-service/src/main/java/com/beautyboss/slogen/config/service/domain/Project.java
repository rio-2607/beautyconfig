package com.beautyboss.slogen.config.service.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Data
public class Project extends BaseDO {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目描述
     */
    private String desc;


    /**
     * 项目管理员
     */
    private List<String> admins;



}

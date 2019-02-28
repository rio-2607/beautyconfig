package com.beautyboss.slogen.config.web.dto;

import lombok.Data;

import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Data
public class ProjectDTO {

    private Long id;

    private String name;

    private String desc;

    private List<String> admins;


}

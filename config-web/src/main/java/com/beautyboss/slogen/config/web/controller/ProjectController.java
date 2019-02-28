package com.beautyboss.slogen.config.web.controller;

import com.beautyboss.slogen.config.common.Response;
import com.beautyboss.slogen.config.web.dto.ProjectDTO;
import com.beautyboss.slogen.config.web.process.ProjectProcess;
import com.beautyboss.slogen.config.web.response.QueryProjectResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Resource
    private ProjectProcess projectProcess;

    @PostMapping("/save")
    public Response save(@RequestBody ProjectDTO request) {
        Long id = projectProcess.save(request);
        return Response.successResponse(id);
    }

    @GetMapping("/query")
    public QueryProjectResponse query(@RequestParam("id") Long id) {
        ProjectDTO result = projectProcess.query(id);
        return new QueryProjectResponse().success(result);
    }

    @PostMapping("/update")
    public Response update(@RequestBody ProjectDTO request) {
        boolean result = projectProcess.update(request);
        return Response.successResponse(result);
    }

    @GetMapping("/delete")
    public Response delete(@RequestParam("id") Long id) {
        boolean result = projectProcess.delete(id);
        return Response.successResponse(result);
    }


}

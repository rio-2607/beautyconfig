package com.beautyboss.slogen.config.web.controller;

import com.beautyboss.slogen.config.common.Response;
import com.beautyboss.slogen.config.web.dto.GroupDTO;
import com.beautyboss.slogen.config.web.process.GroupProcess;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@RestController
@RequestMapping("/api/group")
public class GroupController {

    @Resource
    private GroupProcess groupProcess;

    @PostMapping("/add")
    public Response add(@RequestBody GroupDTO request) {
        Long id = groupProcess.create(request);
        return Response.successResponse(id);
    }

    @GetMapping("/delete")
    public Response delete(@RequestParam("id") Long id) {
        boolean result = groupProcess.delete(id);
        return Response.successResponse(result);
    }

    @GetMapping("/query")
    public Response query(@RequestParam("id") Long id) {
        GroupDTO group = groupProcess.query(id);
        return Response.successResponse(group);
    }

    @GetMapping("/queryAll")
    public Response queryAll(@RequestParam("projectId") Long projectId) {
        List<GroupDTO> groupDTOS = groupProcess.queryAll(projectId);
        return Response.successResponse(groupDTOS);
    }

    @PostMapping("/update")
    public Response update(@RequestBody GroupDTO request) {
        boolean result = groupProcess.update(request);
        return Response.successResponse(result);
    }


}

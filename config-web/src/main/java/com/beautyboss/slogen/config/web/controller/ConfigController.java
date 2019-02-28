package com.beautyboss.slogen.config.web.controller;

import com.beautyboss.slogen.config.common.Response;
import com.beautyboss.slogen.config.web.dto.ConfigDTO;
import com.beautyboss.slogen.config.web.dto.QueryConfigResultDTO;
import com.beautyboss.slogen.config.web.dto.SaveConfigInstanceResultDTO;
import com.beautyboss.slogen.config.web.dto.SaveConfigResultDTO;
import com.beautyboss.slogen.config.web.process.ConfigProcess;
import com.beautyboss.slogen.config.web.response.SaveConfigResponse;
import com.beautyboss.slogen.config.web.response.UpdateConfigResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Resource
    private ConfigProcess configProcess;

    @PostMapping("/save")
    public SaveConfigResponse save(@RequestBody ConfigDTO request) {
        SaveConfigResultDTO result = configProcess.save(request);
        return new SaveConfigResponse().success(result);
    }

    @PostMapping("/update")
    public UpdateConfigResponse update(@RequestBody ConfigDTO request) {
        Map<Integer,SaveConfigInstanceResultDTO> result = configProcess.update(request);
        return new UpdateConfigResponse().success(result);
    }

    @GetMapping("/delete")
    public Response delete(@RequestParam("id") Long id) {
        boolean result = configProcess.delete(id);
        return Response.successResponse(result);
    }

    @GetMapping("/queryById")
    public Response queryById(@RequestParam("id") Long id) {
        QueryConfigResultDTO result = configProcess.queryById(id);
        return Response.successResponse(result);
    }

    @GetMapping("/queryByApp")
    public Response queryByApp(@RequestParam("projectId") Long projectId,
                               @RequestParam("groupId") Integer groupId) {
        List<QueryConfigResultDTO> results = configProcess.query(projectId, groupId);
        return Response.successResponse(results);
    }

}

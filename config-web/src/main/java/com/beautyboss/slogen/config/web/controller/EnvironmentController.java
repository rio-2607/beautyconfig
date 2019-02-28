package com.beautyboss.slogen.config.web.controller;

import com.beautyboss.slogen.config.common.Response;
import com.beautyboss.slogen.config.web.dto.EnvDTO;
import com.beautyboss.slogen.config.web.process.EnvironmentProcess;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@RestController
@RequestMapping("/api/env")
public class EnvironmentController {

    @Resource
    private EnvironmentProcess environmentProcess;

    @PostMapping("/save")
    public Response save(@RequestBody EnvDTO request) {
        Long id = environmentProcess.save(request);
        return Response.successResponse(id);
    }

    @PostMapping("/update")
    public Response update(@RequestBody EnvDTO request) {
        boolean result = environmentProcess.update(request);
        return Response.successResponse(result);
    }

    @GetMapping("/query")
    public Response query(@RequestParam("id") Integer id) {
        EnvDTO result = environmentProcess.query(id);
        return Response.successResponse(result);
    }

    @GetMapping("/queryAll")
    public Response queryAll() {
        List<EnvDTO> results = environmentProcess.queryAll();
        return Response.successResponse(results);
    }

    @GetMapping("/delete")
    public Response delete(@RequestParam("id") Integer id) {
        boolean result = environmentProcess.delete(id);
        return Response.successResponse(result);
    }

}

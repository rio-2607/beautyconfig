package com.beautyboss.slogen.config.web.process;

import com.beautyboss.slogen.config.resource.transfer.Transfer;
import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.service.EnvironmentService;
import com.beautyboss.slogen.config.web.dto.EnvDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Service
public class EnvironmentProcess {

    @Resource
    private EnvironmentService environmentService;

    public Long save(EnvDTO request) {
        Environment env = Transfer.transfer(request,Environment.class);
        return environmentService.save(env);
    }

    public EnvDTO query(Integer id) {
        Environment env = environmentService.queryEnvById(id);
        return Transfer.transfer(env,EnvDTO.class);
    }

    public List<EnvDTO> queryAll() {
        List<Environment> envs = environmentService.queryAll();
        return envs.stream().map(e -> Transfer.transfer(e,EnvDTO.class)).collect(Collectors.toList());
    }

    public boolean update(EnvDTO request) {
        Environment env = Transfer.transfer(request,Environment.class);
        return environmentService.update(env);
    }

    public boolean delete(Integer id) {
        return environmentService.delete(id);
    }


}

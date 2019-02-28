package com.beautyboss.slogen.config.web.process;

import com.beautyboss.slogen.config.common.enums.StatusCode;
import com.beautyboss.slogen.config.common.exceptions.ConfigException;
import com.beautyboss.slogen.config.resource.transfer.Transfer;
import com.beautyboss.slogen.config.service.domain.*;
import com.beautyboss.slogen.config.service.service.ConfigService;
import com.beautyboss.slogen.config.service.service.EnvironmentService;
import com.beautyboss.slogen.config.service.service.GroupService;
import com.beautyboss.slogen.config.service.service.ProjectService;
import com.beautyboss.slogen.config.web.dto.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/23
 */
@Service
@Slf4j
public class ConfigProcess {

    @Resource
    private ConfigService configService;

    @Resource
    private GroupService groupService;

    @Resource
    private ProjectService projectService;

    @Resource
    private EnvironmentService environmentService;

    public SaveConfigResultDTO save(ConfigDTO request) {
        Project project = projectService.query(request.getProjectId());
        if(null == project){
            throw new ConfigException(StatusCode.PROJECT_NOT_EXIST,request.getProjectId());
        }
        Group group = groupService.query(request.getGroupId());
        if(null == group) {
            throw new ConfigException(StatusCode.GROUP_NOT_EXIST,request.getGroupId());
        }
        Config config = convert2Config(request);
        configService.createConfig(config);
        SaveConfigResultDTO result = SaveConfigResultDTO.create(config.getId());
        Map<Integer,List<Environment>> envMap = environmentService.queryEnvMap();
        request.getEnvIds().forEach(envId -> {
            try {
                ConfigInstance instance = convert2Instance(request,config,envId);
                configService.createConfigInstance(instance);
                result.getEnvResult().put(envId, SaveConfigInstanceResultDTO.create(true,""));
            } catch (Exception e) {
                String env = envMap.get(envId).get(0).getName();
                log.error("创建配置[key=" + config.getKey() + ",env = " + env + "]失败");
                result.getEnvResult().put(envId,SaveConfigInstanceResultDTO.create(false,e.getMessage()));
            }
        });
        return result;
    }

    public Map<Integer,SaveConfigInstanceResultDTO> update(ConfigDTO request) {
        Map<Integer,SaveConfigInstanceResultDTO> result = Maps.newHashMap();
        Config config = configService.queryConfig(request.getId());
        if(null == config) {
            throw new ConfigException(StatusCode.CONFIG_NOT_EXIST,request.getId());
        }
        configService.updateConfig(config);
        request.getEnvIds().forEach(envId -> {
            try {
                ConfigInstance instance = configService.queryInstance(config.getId(),envId);
                if(null != instance && !request.getValue().equals(instance.getValue())) {
                    // 只有数据的确发生了变化才去更新数据库以及推送到远程
                    instance.setValue(request.getValue());
                    configService.updateConfigInstance(instance);
                    result.put(envId,SaveConfigInstanceResultDTO.create(true,""));
                } else {
                    result.put(envId,SaveConfigInstanceResultDTO.create(true,"数据未发生变化，未做更新"));
                }
            } catch (Exception e) {
                result.put(envId,SaveConfigInstanceResultDTO.create(false,e.getMessage()));
            }
        });
        return result;
    }

    public boolean delete(Long configId) {
        boolean deleteConfigResult = configService.deleteConfig(configId);
        boolean deleteInstanceResult = configService.deleteConfigInstance(configId);
        return deleteConfigResult && deleteInstanceResult;
    }

    public QueryConfigResultDTO queryById(Long configId) {
        Config config = configService.queryConfig(configId);
        if(null == config) {
            return null;
        }
        List<ConfigInstance> configInstances = configService.queryInstance(configId);
        return convert2QueryConfigResultDTO(config,configInstances);
    }

    public List<QueryConfigResultDTO> query(Long projectId,Integer groupId) {
        List<Config> configs = configService.queryConfig(projectId,groupId);
        if(CollectionUtils.isEmpty(configs)) {
            return Collections.EMPTY_LIST;
        }
        return configs.stream()
                .map(c -> queryById(c.getId()))
                .collect(Collectors.toList());
    }


    private Config convert2Config(ConfigDTO dto) {
        return new Config.Builder()
                .key(dto.getKey())
                .desc(dto.getDesc())
                .groupId(dto.getGroupId())
                .projectId(dto.getProjectId())
                .type(dto.getType())
                .build();
    }

    private ConfigInstance convert2Instance(ConfigDTO dto,Config config,Integer envId) {
        return new ConfigInstance.Builder()
                .configId(config.getId())
                .value(dto.getValue())
                .envId(envId)
                .build();
    }

    private QueryConfigResultDTO convert2QueryConfigResultDTO(Config config,List<ConfigInstance> instances) {
        QueryConfigResultDTO result = Transfer.transfer(config,QueryConfigResultDTO.class);
        List<QueryConfigInstanceDTO> queryConfigInstanceDTOS =
                instances.stream().map(i -> Transfer.transfer(i,QueryConfigInstanceDTO.class)).collect(Collectors.toList());
        result.setInstances(queryConfigInstanceDTOS);
        return result;
    }

}

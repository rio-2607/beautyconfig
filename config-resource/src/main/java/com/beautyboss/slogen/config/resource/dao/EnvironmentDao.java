package com.beautyboss.slogen.config.resource.dao;

import com.beautyboss.slogen.config.resource.entity.EnvironmentEntity;
import com.beautyboss.slogen.config.resource.mapper.EnvironmentMapper;
import com.beautyboss.slogen.config.resource.transfer.EnvironmentTransfer;
import com.beautyboss.slogen.config.resource.transfer.Transfer;
import com.beautyboss.slogen.config.service.domain.Environment;
import com.beautyboss.slogen.config.service.repository.EnvironmentRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Repository
public class EnvironmentDao implements EnvironmentRepository{

    @Resource
    private EnvironmentMapper environmentMapper;

    @Override
    public Long create(Environment environment) {
        EnvironmentEntity entitiy = EnvironmentTransfer.transfer2Entity(environment);
        environmentMapper.create(entitiy);
        return entitiy.getId();
    }

    @Override
    public Environment query(Integer id) {
        EnvironmentEntity entity = environmentMapper.query(id);
        return EnvironmentTransfer.transfer2DO(entity);
    }

    @Override
    public boolean modify(Environment environment) {
        EnvironmentEntity entity = EnvironmentTransfer.transfer2Entity(environment);
        return 1 == environmentMapper.modify(entity);
    }

    @Override
    public boolean delete(Integer id) {
        return 1 == environmentMapper.delete(id);
    }

    @Override
    public List<Environment> queryAll() {
        List<EnvironmentEntity> entities = environmentMapper.queryAll();
        return entities.stream().map(EnvironmentTransfer::transfer2DO).collect(Collectors.toList());
    }
}

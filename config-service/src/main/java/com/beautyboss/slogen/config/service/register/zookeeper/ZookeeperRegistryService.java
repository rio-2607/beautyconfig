package com.beautyboss.slogen.config.service.register.zookeeper;

import com.beautyboss.slogen.config.common.utils.CodecUtils;
import com.beautyboss.slogen.config.service.enums.RegisterTypeEnum;
import com.beautyboss.slogen.config.service.register.ConfigRegisterService;
import com.beautyboss.slogen.config.service.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * Author : Slogen
 * Date   : 2019/2/12
 */
@Slf4j
public class ZookeeperRegistryService implements ConfigRegisterService {

    private int sessionTimeout = 60000;

    private final String serverIps;

    private String parentPath = "/SLOGEN/BEAUTYCONFIG";

    private String contextNode = "CONTEXTNODE";

    private String timeStampNode = "TIMESTAMP";

    private String charset = "UTF-8";

    private CuratorFramework curatorClient;

    String tempIp = "";

    public ZookeeperRegistryService(String serverIps) {
        this.serverIps = serverIps;
    }

    public void init() {
        curatorClient = CuratorFrameworkFactory.newClient(serverIps,sessionTimeout,30 * 1000,
                new RetryNTimes(1,1000));
        tempIp = serverIps;
        curatorClient.getConnectionStateListenable().addListener(
                (curatorFramework,connectionState) -> {

        });
        curatorClient.start();
    }

    @Override
    public void destory() {
        this.curatorClient.close();
    }

    @Override
    public RegisterTypeEnum type() {
        return RegisterTypeEnum.Zookeeper;
    }

    @Override
    public void registerAndPushValue(String key, String value, long timeStamp) {
        try {
            set(parentPath + "/" + key + "/" + timeStampNode, timeStamp);
            set(parentPath + "/" + key + "/" + contextNode, value);
        } catch (Exception e) {
            log.error("",e.getMessage());

        }
    }

    private void set(String path, long value) throws Exception {
        set(path, CodecUtils.getLongBytes(value));
    }

    public void set(String path,String value) throws Exception {
        if(null != value) {
            value = SecurityUtils.tryDecode(value);
            set(path,value.getBytes(charset));
        } else {
//            logger
        }
    }

    private void set(String path,byte[] value) throws Exception {
        if(exists(path)) {
            curatorClient.setData().forPath(path,value);
        } else {
            curatorClient.create().creatingParentsIfNeeded().forPath(path,value);
        }
    }

    private boolean exists(String path) throws Exception {
        return null != curatorClient.checkExists().forPath(path);
    }
}

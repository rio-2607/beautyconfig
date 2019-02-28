package com.beautyboss.slogen.config.client.loader.zookeeper;

import com.beautyboss.slogen.config.client.Constants;
import com.beautyboss.slogen.config.client.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
@Slf4j
public class ZookeeperDataWatcher implements CuratorListener {


    private ZookeeperConfigLoader configLoader;

    public ZookeeperDataWatcher(ZookeeperConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    @Override
    public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
        if (event.getType() == CuratorEventType.WATCHED) {
            WatchedEvent watchedEvent = event.getWatchedEvent();
            if (watchedEvent.getPath() != null) {
                try {
                    process(watchedEvent);
                } catch (Exception e) {
                    log.error("process event error",e);
                }
            }
        }
    }

    public void process(WatchedEvent event) throws Exception {
        if(StringUtils.isEmpty(event.getPath()) || !Constants.CONFIG_PATH.equals(event.getPath())) {
            // 路径为空或者不是配置所在的路径，则直接返回，不用处理
            return;
        }
        String key = event.getPath().substring(Constants.CONFIG_PATH.length() + 1);

        if (StringUtils.isEmpty(key)) {
            return;
        }
        if (event.getType() == Watcher.Event.EventType.NodeCreated
                || event.getType() == Watcher.Event.EventType.NodeDataChanged) {
            processConfigChanged(key, event.getType());
        } else if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
            processConfigDeleted(key);
        }
    }

    private void processConfigChanged(String key, Watcher.Event.EventType eventType) throws Exception {
        if(!configLoader.getKeyValueMap().containsKey(key)) {
            log.info("ignore config event,key = ", key);
            // 本地缓存中不包含数据，则不需要关系
            return;
        }
        ZookeeperValue zkValue = configLoader.getValue(key);
        if(null == zkValue) {
            log.error("failed to get config,key = ",key);
            return;
        }
        if(acceptChange(eventType,key,zkValue)) {
            configLoader.configChanged(key,zkValue);
        }
    }

    private void processConfigDeleted(String key) throws Exception {
        ZookeeperValue zkValue = configLoader.getZookeeperValue(key);
        if (zkValue == null) {
            configLoader.configDeleted(key);
        } else {
            configLoader.configChanged(key, zkValue);
        }
    }

    private boolean acceptChange(Watcher.Event.EventType eventType, String key, ZookeeperValue currentValue) {
        if (eventType == Watcher.Event.EventType.NodeCreated && StringUtils.isEmpty(currentValue.getValue()))
            return false;
        return configLoader.acceptChange(key, currentValue);
    }

}

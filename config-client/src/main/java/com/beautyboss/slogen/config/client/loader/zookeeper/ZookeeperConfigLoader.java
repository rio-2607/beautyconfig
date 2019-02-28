package com.beautyboss.slogen.config.client.loader.zookeeper;

import com.beautyboss.slogen.config.client.ConfigEvent;
import com.beautyboss.slogen.config.client.ConfigValue;
import com.beautyboss.slogen.config.client.Constants;
import com.beautyboss.slogen.config.client.Environment;
import com.beautyboss.slogen.config.client.loader.AbstractConfigLoader;
import com.beautyboss.slogen.config.client.manager.ConfigContext;
import com.beautyboss.slogen.config.client.util.CodecUtils;
import com.beautyboss.slogen.config.client.util.NamedThreadFactory;
import com.beautyboss.slogen.config.client.util.ZookeeperUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.ensemble.fixed.FixedEnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.zookeeper.ZooKeeper;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
@Slf4j
public class ZookeeperConfigLoader extends AbstractConfigLoader {

    private String appNmae = Environment.getAppName();

    private String env = Environment.getEnv();

    private static final int DEFAULT_SYNC_DELAY_MAX_SECONDS = 30;

    private String zookeeperAddress;
    private String zookeeperAccount;
    private String zookeeperPassword;

    private volatile CuratorFramework curatorClient;

    private volatile int randomSyncInterval;

    private volatile ZookeeperOperation zookeeperOperation;

    private ConcurrentMap<String,ZookeeperValue> keyValueMap;

    private volatile ExecutorService syncExecutor;

    private AtomicBoolean isSyncing = new AtomicBoolean(false);

    private volatile long lastSyncTime = System.currentTimeMillis();

    private Random random = new Random();

    public ZookeeperConfigLoader(String zookeeperAddress,String zookeeperAccount,
                                 String zookeeperPassword) {
        this.zookeeperAccount = zookeeperAccount;
        this.zookeeperPassword = zookeeperPassword;
        this.zookeeperAddress = zookeeperAddress;
        initialize();
    }

    public void initialize() {
        keyValueMap = new ConcurrentHashMap<>();
        curatorClient = newCuratorClient(this.zookeeperAddress);
        zookeeperOperation = new ZookeeperOperation(curatorClient);
        syncExecutor = Executors
                .newSingleThreadExecutor(new NamedThreadFactory("config-zookeeper-sync", true));
        startConfigSyncTask();
    }

    private void startConfigSyncTask() {
        setSyncInterval();
        syncExecutor.submit(() -> {
            try {
                Thread.sleep(1000);
                syncConfig();
            } catch (Exception e) {

            }
        });

    }

    private void setSyncInterval() {
        randomSyncInterval = 1000 * (5 + random.nextInt(10)) / 10;
    }
    private CuratorFramework newCuratorClient(String zkAddress) {
        final CuratorFramework curatorClient = CuratorFrameworkFactory.builder()
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(new RandomRetry(3, 30 * 1000))
                .ensembleProvider(new FixedEnsembleProvider(zkAddress))
                .build();
        curatorClient.getConnectionStateListenable().addListener((client,newState) ->  {
            if (newState == ConnectionState.RECONNECTED) {
                try {
                    trySyncConfig();
                } catch (Exception e) {
                }
            } else if (newState == ConnectionState.CONNECTED) {
                if (zookeeperAccount != null && zookeeperPassword != null) {
                    try {
                        ZooKeeper zooKeeper = client.getZookeeperClient().getZooKeeper();
                        zooKeeper.addAuthInfo("digest",
                                (zookeeperAccount + ":" + zookeeperPassword).getBytes());
                    } catch (Exception e) {
                    }
                }
            }
        });

        curatorClient.getCuratorListenable().addListener(new ZookeeperDataWatcher(this));
        curatorClient.start();
        return curatorClient;
    }

    private void trySyncConfig() {
        long now = System.currentTimeMillis();
        if (now - lastSyncTime >= randomSyncInterval) {
            if (isSyncing.compareAndSet(false, true)) {
                try {
                    TimeUnit.SECONDS.sleep(random.nextInt(DEFAULT_SYNC_DELAY_MAX_SECONDS));
                    syncConfig();
                    lastSyncTime = System.currentTimeMillis();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (Exception e) {
                    log.error("failed to sync config.",e);
                    lastSyncTime = System.currentTimeMillis() - randomSyncInterval / 2;
                } finally {
                    isSyncing.set(false);
                    setSyncInterval();
                }
            }
        }
    }

    public ZookeeperValue getValue(String key) throws Exception {
        String path = ZookeeperUtils.getConfigPath(key);
        String timestampPath = ZookeeperUtils.getTimestampPath(path);
        ZookeeperValue zkValue = null;
        try {
            byte[] data = zookeeperOperation.getDataWatched(path);

            if (data != null) {
                zkValue = new ZookeeperValue();
                String value = new String(data, Constants.CHARSET);
                zkValue.setValue(value);
                data = zookeeperOperation.getData(timestampPath);
                if (data != null) {
                    Long timestamp = CodecUtils.getLong(data);
                    zkValue.setTimestamp(timestamp);
                }
            }
            return zkValue;
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }


//    private ZookeeperValue getValue(String key, String group) throws Exception {
//        String path = ZookeeperUtils.getConfigPath(key);
//        String timestampPath = ZookeeperUtils.getTimestampPath(path);
//        ZookeeperValue zkValue = null;
//
//        try {
//            byte[] data = zookeeperOperation.getDataWatched(path);
//
//            if (data != null) {
//                zkValue = new ZookeeperValue();
//                String value = new String(data, Constants.CHARSET);
//                zkValue.setValue(value);
//                data = zookeeperOperation.getData(timestampPath);
//                if (data != null) {
//                    Long timestamp = CodecUtils.getLong(data);
//                    zkValue.setTimestamp(timestamp);
//                }
//            }
//            return zkValue;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//        }
//    }

    boolean acceptChange(String key, ZookeeperValue currentValue) {
        ZookeeperValue existValue = keyValueMap.get(key);
        return (existValue == null ||
                currentValue.getTimestamp() > existValue.getTimestamp());
    }

    private void syncConfig() throws Exception {
        if(!curatorClient.getZookeeperClient().isConnected()) {
            return;
        }
        keyValueMap.forEach((key,currentValue) -> {
           try {
               ZookeeperValue fetchedZkValue = getZookeeperValue(key);
               if(null == fetchedZkValue) {
                   if(null != currentValue && null != currentValue.getValue()) {
                       configDeleted(key);
                   }
               } else {
                   if(acceptChange(key,fetchedZkValue)) {
                       configChanged(key,fetchedZkValue);
                   }
               }
           } catch (Exception e) {
           }
        });


    }

    public ConcurrentMap<String, ZookeeperValue> getKeyValueMap() {
        return keyValueMap;
    }

    public ZookeeperValue getZookeeperValue(String key) throws Exception {
        return getValue(key);
    }

    public void configDeleted(String key) {
        ZookeeperValue oldZkValue = keyValueMap.put(key, ZookeeperValue.NULL_VALUE);
        ConfigEvent ce = new ConfigEvent(key, null, toConfigValue(key, oldZkValue));
        fireConfigChanged(ce);
    }

    public void configChanged(String key,ZookeeperValue zkValue) {
        ZookeeperValue oldValue = keyValueMap.get(key);
        ConfigEvent configEvent = new ConfigEvent(key,toConfigValue(key,zkValue),toConfigValue(key,oldValue));
        fireConfigChanged(configEvent);

    }

    private ConfigValue toConfigValue(String key, ZookeeperValue zkValue) {
        if (zkValue == null || zkValue.isNullValue()) {
            return null;
        }
        ConfigValue value = new ConfigValue(key, zkValue.getValue(), getName());
        value.setModifyTime(zkValue.getTimestamp());
        return value;
    }

    @Override
    public ConfigValue get(String key, ConfigContext context) throws Exception {
        try {
            ZookeeperValue zkValue = null;
            if(context.isUseCache()) {
                zkValue = keyValueMap.get(key);
            }
            if(null == zkValue) {
                zkValue = getZookeeperValue(key);
                if(context.isUseCache()) {
                    keyValueMap.put(key,null == zkValue ? ZookeeperValue.NULL_VALUE:zkValue);
                }
            }
            return toConfigValue(key,zkValue);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public String getName() {
        return "zookeeper";
    }
}

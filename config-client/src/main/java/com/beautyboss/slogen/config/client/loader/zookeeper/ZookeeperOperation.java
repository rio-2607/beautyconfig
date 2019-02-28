package com.beautyboss.slogen.config.client.loader.zookeeper;

import com.beautyboss.slogen.config.client.Constants;
import com.beautyboss.slogen.config.client.util.CodecUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.KeeperException;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public class ZookeeperOperation {

    private CuratorFramework curatorClient;

    public ZookeeperOperation(){

    }

    public ZookeeperOperation(CuratorFramework curatorClient) {
        this.curatorClient = curatorClient;
        if(!curatorClient.isStarted()) {
            curatorClient.start();
        }
    }

    public byte[] getData(String path) throws Exception {
        try {
            return curatorClient.getData().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            return null;
        }
    }


    public byte[] getDataWatched(String path) throws Exception {
        try {
            byte[] data = curatorClient.getData().watched().forPath(path);
            return data;
        } catch (KeeperException.NoNodeException e) {
            curatorClient.checkExists().watched().forPath(path);
            return null;
        }
    }

    public void setData(String path, String value) throws Exception {
        setData(path, value == null ? new byte[0] : value.getBytes(Constants.CHARSET));
    }

    public void setData(String path, long value) throws Exception {
        setData(path, CodecUtils.getLongBytes(value));
    }

    public void setData(String path,byte[] data) throws Exception {
        try {
            curatorClient.setData().forPath(path,data);
        } catch (KeeperException.NoNodeException e) {
            curatorClient.create().creatingParentsIfNeeded().forPath(path,data);
        }
    }

    public void delete(String path) throws Exception {
        try {
            curatorClient.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (KeeperException.NoNodeException e) {
        }
    }
}

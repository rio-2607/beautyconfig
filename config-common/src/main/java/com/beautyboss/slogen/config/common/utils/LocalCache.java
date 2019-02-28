package com.beautyboss.slogen.config.common.utils;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

/**
 * Author : Slogen
 * Date   : 2019/2/14
 */
public class LocalCache<K,V> {

    private long expire;

    private ConcurrentLinkedHashMap<K,Node<V>> store;

    public LocalCache(int maxSize,long expire) {
        this.expire = expire;
        ConcurrentLinkedHashMap.Builder builder = new ConcurrentLinkedHashMap.Builder<K,Node<V>> ();
        builder.initialCapacity(maxSize / 4);
        builder.maximumWeightedCapacity(maxSize);
        builder.weigher(Weighers.singleton());
        store = builder.build();
    }

    public V get(K key) {
        Node<V> node = store.get(key);
        if(null == node) {
            return null;
        }
        if(node.getExpire() < System.currentTimeMillis()) {
            store.remove(key);
            return null;
        }
        return node.getV();
    }

    public void put(K key,V value) {
        Node node = new Node();
        node.setExpire(this.expire + System.currentTimeMillis());
        node.setV(value);
        store.put(key,node);
    }

    public V remove(K key) {
        Node<V> node = store.get(key);
        if(null == node) {
            return null;
        }
        store.remove(key);
        return node.getV();
    }

    public void setMaxsize(int maxSize) {
        this.store.setCapacity(maxSize);
    }

    public int getMaxSize() {
        return (int) store.capacity();
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    private class Node<V> {

        private V v;
        private long expire;

        public V getV() {
            return v;
        }

        public void setV(V v) {
            this.v = v;
        }

        public long getExpire() {
            return expire;
        }

        public void setExpire(long expire) {
            this.expire = expire;
        }
    }
}

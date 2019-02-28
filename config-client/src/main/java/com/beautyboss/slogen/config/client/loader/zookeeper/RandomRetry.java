package com.beautyboss.slogen.config.client.loader.zookeeper;

import org.apache.curator.retry.RetryNTimes;

import java.util.Random;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public class RandomRetry extends RetryNTimes {

    private Random random = new Random();

    private int sleepMsBetweenRetries;

    public RandomRetry(int n, int sleepMsBetweenRetries) {
        super(n, sleepMsBetweenRetries);
        this.sleepMsBetweenRetries = sleepMsBetweenRetries;
    }

    @Override
    protected long getSleepTimeMs(int retryCount, long elapsedTimeMs) {
        return random.nextInt(sleepMsBetweenRetries);
    }

}

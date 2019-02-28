package com.beautyboss.slogen.config.client;

import com.beautyboss.slogen.config.client.util.NamedThreadFactory;

import java.util.concurrent.*;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public class TaskManager {

    private static ScheduledExecutorService scheduledExecutorService = Executors
            .newSingleThreadScheduledExecutor(new NamedThreadFactory("lion-task-scheduler", true));

    private static ExecutorService executorService = Executors
            .newSingleThreadExecutor(new NamedThreadFactory("lion-task-runner", true));

    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                            long initialDelay,
                                                            long delay,
                                                            TimeUnit unit) {
        return scheduledExecutorService.scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public static Future<?> submit(Runnable task) {
        return executorService.submit(task);
    }

}

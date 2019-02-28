package com.beautyboss.slogen.config.client;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public class RunnableConfigListener implements Runnable {

//    private static Logger logger = LoggerFactory.getLogger(RunnableConfigListener.class);

    private ConfigListener configListener;
    private ConfigEvent configEvent;

    public RunnableConfigListener(ConfigListener configListener, ConfigEvent configEvent) {
        if (configListener == null) {
            throw new NullPointerException("config listener is null");
        }
        if (configEvent == null) {
            throw new NullPointerException("config event is null");
        }
        this.configListener = configListener;
        this.configEvent = configEvent;
    }

    @Override
    public void run() {
        try {
            configListener.configChanged(configEvent);
        } catch (Throwable t) {
//            logger.error("failed to notify config listener: " + configEvent, t);
        }
    }

}

package com.beautyboss.slogen.config.client;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {
        String value = ConfigClient.get("whitelist1","test");
        System.out.println("配置数据为 :" + value);
        Thread.sleep(1000000);
    }
}

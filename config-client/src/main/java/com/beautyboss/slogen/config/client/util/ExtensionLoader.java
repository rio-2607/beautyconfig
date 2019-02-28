package com.beautyboss.slogen.config.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public class ExtensionLoader {

    private static Map<Class<?>, Object> extensionMap = new ConcurrentHashMap<>();
    private static Map<Class<?>,List<?>> extensionListMap = new ConcurrentHashMap<>();

    public static <T> T getExtension(Class<T> clazz) {
        T extension = (T) extensionMap.get(clazz);
        if(null == extension) {
            extension = newExtension(clazz);
            extensionMap.put(clazz, extension);
        }
        return extension;
    }

    public static <T> List<T> getExtensionList(Class<T> clazz) {
        List<T> extensions = (List<T>) extensionListMap.get(clazz);
        if (extensions == null) {
            extensions = newExtensionList(clazz);
            if (!extensions.isEmpty()) {
                extensionListMap.put(clazz, extensions);
            }
        }
        return extensions;
    }

    public static <T> T newExtension(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        for(T service : serviceLoader) {
            return service;
        }
        return null;
    }

    public static <T> List<T> newExtensionList(Class<T> clazz) {
        ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
        List<T> extensions = new ArrayList<T>();
        for (T service : serviceLoader) {
            extensions.add(service);
        }
        return extensions;
    }
}

package com.beautyboss.slogen.config.client.util;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

/**
 * Author : Slogen
 * Date   : 2019/2/26
 */
public class PropertiesUtils {

    private static final String SCHEMA_CLASSPATH = "classpath:";
    private static final String SCHEMA_FILE = "file:";

    public static Properties load(String resource) throws IOException {
        if (resource == null) {
            throw new NullPointerException("resource is null");
        }
        if (resource.startsWith(SCHEMA_CLASSPATH)) {
            return loadFromClassPath(resource.substring(SCHEMA_CLASSPATH.length()));
        } else if (resource.startsWith(SCHEMA_FILE)) {
            return loadFromFileSystem(resource.substring(SCHEMA_FILE.length()));
        } else {
            Properties props = loadFromClassPath(resource);
            if (props == null) {
                props = loadFromFileSystem(resource);
            }
            return props;
        }
    }

    public static Properties loadFromFileSystem(String file) throws IOException {
        File f = new File(file);
        if(!f.exists()) {
            return null;
        }
        URL url = f.toURI().toURL();
        return load(url);
    }

    public static Properties loadFromClassPath(String file) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(file);
        if(url == null) {
            return null;
        }
        return load(url);
    }

    public static Properties load(URL url) throws IOException {
        InputStream in = null;
        try {
            in = url.openStream();
            Properties props = new Properties();
            props.load(in);
            return props;
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void save(Map<String, String> properties, String fileName) throws IOException {
        if (properties == null) {
            throw new NullPointerException("properties is null");
        }
        if (fileName == null) {
            throw new NullPointerException("file is null");
        }
        File file = new File(fileName);
        if (!file.exists()) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            Properties props = new Properties();
            props.putAll(properties);
            props.store(writer, null);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static String get(Map<String, String> properties, String key, String defaultValue) {
        if (properties == null) {
            return null;
        }
        String value = properties.get(key);
        return value == null ? defaultValue : value;
    }

    public static Boolean getBoolean(Map<String, String> properties, String key,
                                     Boolean defaultValue) {
        if (properties == null) {
            return null;
        }
        String value = properties.get(key);
        return value == null ? defaultValue : Boolean.valueOf(value);
    }

    public static Integer getInt(Map<String,String> properties,String key,Integer defaultValue){
        if (properties == null) {
            return null;
        }
        String value = properties.get(key);
        return value == null ? defaultValue : Integer.valueOf(value);
    }
}

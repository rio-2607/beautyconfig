## beautyconfig

简单的配置管理中间件，支持redis和zookeeper


## 使用 

### 1. 添加配置

在后台[配置后台(开发中)](http://beautyboss.me/)进行配置。

需要新建应用，然后在新建的应用里面新建配置。

每个应用属于一个特定的group，新建应用的时候会生成一个默认的group。

### 2. 引入maven依赖

```
<dependency>
    <groupId>com.beautyboss.slogen</groupId>
    <artifactId>config-client</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
```

### 3. 在配置文件中添加下面两个配置

config.app=appName

config.env=dev

config.engine=redis


appName是第一步填写的应用名。

env代表环境，有dev,test,pre,prod。可以根据需要新增。

config.engine表示底层使用的配置引擎，目前支持redis和zookeeper。


### 4. 获取配置

```java
String value = ConfigClient.get(key,defaultValue);
```

支持以下几个接口

```java
public static String get(String key); 
public static String get(String key, String defaultValue);
public static String get(String key, String defaultValue, ConfigContext context);
public static String getStringValue(String key);
public static String getStringValue(String key, String defaultValue);
public static byte getByteValue(String key);
public static Byte getByte(String key);
public static byte getByteValue(String key, byte defaultValue);
public static short getShortValue(String key);
public static Short getShort(String key);
public static short getShortValue(String key, short defaultValue);
public static int getIntValue(String key);
public static Integer getInt(String key);
public static int getIntValue(String key, int defaultValue);
public static long getLongValue(String key) ;
public static Long getLong(String key);
public static long getLongValue(String key, long defaultValue);
public static float getFloatValue(String key);
public static Float getFloat(String key);
public static float getFloatValue(String key, float defaultValue);
public static double getDoubleValue(String key);
public static Double getDouble(String key);
public static double getDoubleValue(String key, double defaultValue) ;
public static boolean getBooleanValue(String key);
public static Boolean getBoolean(String key);
public static boolean getBooleanValue(String key, boolean defaultValue);
public static List<String> getList(String key);
public static <T> List<T> getList(String key, Class<T> clazz);
public static <T> List<T> getList(String key, Class<T> clazz, List<T> defaultValue) ;
public static Map<String, String> getMap(String key) ;
public static <T> Map<String, T> getMap(String key, Class<T> clazz);
public static <T> Map<String, T> getMap(String key, Class<T> clazz,Map<String, T> defaultValue);
public static <T> T getBean(String key, Class<T> clazz);
public static <T> T getBean(String key, Class<T> clazz, T defaultValue);

```

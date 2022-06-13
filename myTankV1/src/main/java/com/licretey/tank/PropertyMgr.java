package com.licretey.tank;

import java.io.IOException;
import java.util.Properties;

//封装读取配置文件的方法
public class PropertyMgr {
    private static Properties props;

    // PropertyMgr加载到内存之后首先初始化properties，然后初始化static语句块内容
    static  {
        try {
            props = new Properties();
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String get(String key){
        return (String) props.get(key);
    }
}

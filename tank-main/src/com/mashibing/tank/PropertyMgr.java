package com.mashibing.tank;

import java.io.IOException;
import java.util.Properties;

public class PropertyMgr {

	static Properties props = new Properties();
	
	private static PropertyMgr INSTANCE = new PropertyMgr();
	private PropertyMgr() {}
	public static PropertyMgr getInstance() {
		return INSTANCE;
	}
	
	
	static {
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object getValue(String key) {
		if(props == null) return null;
		return props.get(key);
	}

	public static void main(String[] args) {
		System.out.println(getValue("initTankCount"));
	}
}

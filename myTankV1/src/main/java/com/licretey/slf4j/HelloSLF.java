package com.licretey.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloSLF {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloSLF.class);

        //通过logback.xml (resources目录下)配置输出到哪里记录什么级别日志
        logger.trace("trace");//未显示
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
        logger.error("warn {} +/- {}","aaa","bbb");//使用{}代表变量
    }
}

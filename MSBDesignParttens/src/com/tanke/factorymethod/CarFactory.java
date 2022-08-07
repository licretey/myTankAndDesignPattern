package com.tanke.factorymethod;

/* 工厂方法
        每一种类型创建一个方法
 */
public class CarFactory {
    public Moveable create() {
        System.out.println("a car created!");//日志信息
        return new Car();
    }
}

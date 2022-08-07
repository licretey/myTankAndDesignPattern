package com.tanke.factorymethod;

/**
 * 简单工厂
 *      可扩展性不好（添加新方式的类时需要写固定的方法来new）
 */
public class SimpleVehicleFactory {
    public Car createCar() {
        //before processing
        return new Car();
    }

    public Broom createBroom() {
        return new Broom();
    }
}

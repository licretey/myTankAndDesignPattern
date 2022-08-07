package com.tanke.factorymethod;

import javafx.scene.shape.MoveTo;

public class Main {
    public static void main(String[] args) {
//        Moveable ，公共父类
        Moveable m = new CarFactory().create();// 工厂中处理new的过程
        m.go();
    }
}

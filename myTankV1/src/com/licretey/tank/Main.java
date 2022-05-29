package com.licretey.tank;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        TankFrame.SINGLE_FRAME.setVisible(true);

        //添加声音
        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while (true){
            try {
                TimeUnit.MICROSECONDS.sleep(20000);//sleep的另一种写法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TankFrame.SINGLE_FRAME.repaint(); // repaint()会调用paint()
        }

    }
}

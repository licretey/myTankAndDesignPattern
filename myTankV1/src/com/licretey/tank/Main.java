package com.licretey.tank;

import com.licretey.tank.net.TankClient;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        //显示游戏背景图（包含各种物体：tangk，子弹，爆炸）
        TankFrame.SINGLE_FRAME.setVisible(true);

        //添加声音
//        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        new Thread(()->{
            while (true){
                try {
                    TimeUnit.MICROSECONDS.sleep(20000);//sleep的另一种写法
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TankFrame.SINGLE_FRAME.repaint(); // repaint()会调用paint()
            }
        }).start();

        //反复绘制和联网操作需要使用不同的线程（联网会阻塞）
        TankClient.INSTANCE.connect();//联网


    }
}

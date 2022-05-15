package com.licretey.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TankFrame extends Frame {
    Tank tempTank;                              // tank对象
    Tank enemy;
    private Bullet bullet;                      // 子弹
    public static final int GAME_WIDTH = 1000;  // 界面宽
    public static final int GAME_HEIGHT = 800;  // 界面高

    public TankFrame(){
        this.setTitle("tank war");
        this.setLocation(400,100); // 相对于屏幕window
        this.setSize(GAME_WIDTH,GAME_HEIGHT);

        // 添加键盘监听
        this.addKeyListener(new TankKeyListener());

        // 抽象到tank类中
        this.tempTank = new Tank(100,100, Direction.D, Group.GOOD,this);
        this.enemy = new Tank(300,200, Direction.D, Group.BAD,this);
        // 添加子弹(不可省，放置bullet为空)
        this.bullet = new Bullet(100,100,Direction.D,Group.GOOD);
    }

    // awt自动调用
    // Graphics由系统提供
    @Override
    public void paint(Graphics g) {
        // 绘制方块（x,y相对于窗口）
        // 让这个方块动起来，就需要传入动态的x ，y坐标，并且不停的调用paint绘制（如下）
        //        g.fillRect(x,y,50,50);
        tempTank.paint(g);//x，y由局部变量抽出到一个对象中，这个对象自己去绘制
        enemy.paint(g);
        bullet.paint(g);
    }

    public void addBullet(Bullet bullet){
        this.bullet = bullet;
    }

    private class TankKeyListener extends KeyAdapter {
        // KeyAdapter 以空方法的形式实现许多KeyListener接口的方法
        @Override
        public void keyPressed(KeyEvent e) {
            // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
            tempTank.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
            tempTank.keyReleased(e);
        }
    }

    Image offScreenImage = null; // 一张虚拟图片（放在内存中）
    /**
     * 解决闪烁 双缓冲问题
     * repaint()会调用update()
     */
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics(); //拿到虚拟图片上的画笔
        //设置背景色
        Color tempColor = gOffScreen.getColor(); // 临时保存旧笔色
        gOffScreen.setColor(Color.BLACK);        // 改笔色
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);//填充颜色
        gOffScreen.setColor(tempColor); // 回设笔色

        paint(gOffScreen); //绘制虚拟图片
        // 虚拟图片绘制完全之后，去画到屏幕上（由内存到显存）
        g.drawImage(offScreenImage, 0, 0, null);

    }
}

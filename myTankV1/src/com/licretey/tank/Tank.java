package com.licretey.tank;

import javax.imageio.ImageIO;
import javax.naming.directory.DirContext;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * 面向对象：
 *      抽象出名词：属性、类
 *      抽象出动词：方法
 */
public class Tank {
    // 位置信息
    private int x , y;
    // 速度
    private static final int SPEED = 5;
    // 方向
    private Direction dir = Direction.R;
    // 几个变量，用于记录键盘的按下状态
    private boolean bL,bR,bU,bD;
    // enmy默认在移动
    private boolean moving = true;
    // 使用枚举 区分敌我
    private Group group;
    // 标志tank是否死亡
    private boolean live = true;

    public boolean isLive() {
        return live;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Tank(int x, int y, Direction dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;     // 初始化移动方向，默认R
        this.group = group; // 确定好坏
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    // 移动
    private void move() {
        if(!this.moving) return;
        switch (dir){
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
        }

        // 更新敌人tank随机前进方向
        this.dir = Direction.randomDir();
        fire();
    }

    /**
     * paint、keyPressed两种方法的核心思想就是，将操作物给对象自己去处理（键盘对象给tank，tank自己判断加减；画笔给tank，tank自己绘制位置）
     */
    // 自带绘制方法（根据自己的i位置信息绘制）
    public void paint(Graphics g) {
        if(!this.isLive()) return; //若死亡不再绘制
//        g.fillRect(x,y,50,50);
        //使用图片代替方块
        switch (dir){
            case L:
                g.drawImage(ResourceMgr.badTankL, x, y ,null);
                break;
            case R:
                g.drawImage(ResourceMgr.badTankR, x, y ,null);
                break;
            case U:
                g.drawImage(ResourceMgr.badTankU, x, y ,null);
                break;
            case D:
                g.drawImage(ResourceMgr.badTankD, x, y ,null);
                break;
        }
        this.move();//移动
    }



    // 开火
    private void fire() {
        int bulletX = x + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bulletY = y + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        // 使用tank的参数去创建一个子弹
        Bullet bullet = new Bullet(bulletX, bulletY, dir, group);
        TankFrame.SINGLE_FRAME.addBullet(bullet);
    }

    public void die() {
        this.setLive(false);
    }
}

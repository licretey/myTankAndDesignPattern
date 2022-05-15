package com.licretey.tank;

import java.awt.*;

/*
 * 子弹类
 */
public class Bullet {
    private int x, y;                    // 位置
    private Direction dir;               // 方向
    private Group group;                 // 好坏
    private static final int SPEED = 7;  // 速度

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    private boolean live = true;         // 子弹是否从列表中删除

    public Bullet(int x, int y, Direction dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
    }

    // 子弹自我绘制方法
    public void paint(Graphics g) {
//        g.fillRect(x,y,50,50);
        //使用图片代替方块
        switch (dir){
            case L:
                g.drawImage(ResourceMgr.bulletL, x, y ,null);
                break;
            case R:
                g.drawImage(ResourceMgr.bulletR, x, y ,null);
                break;
            case U:
                g.drawImage(ResourceMgr.bulletU, x, y ,null);
                break;
            case D:
                g.drawImage(ResourceMgr.bulletD, x, y ,null);
                break;
        } 
        move();
    }

    // 移动
    private void move() {
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
        boundsCheck();
    }

    // 边界检查
    private void boundsCheck() {
        if(x<0||y<30||x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT){
            this.live = false;
        }
    }
}

package com.licretey.tank;

import com.licretey.tank.strategy.DefaultFireStrategy;
import com.licretey.tank.strategy.FireStrategy;
import com.licretey.tank.strategy.FourDirFireStrategy;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;

/**
 * 面向对象：
 *      抽象出名词：属性、类
 *      抽象出动词：方法
 */
public class Player {
    // 位置信息
    private int x , y;
    // 速度
    private static final int SPEED = 5;
    // 方向
    private Direction dir = Direction.R;
    // 几个变量，用于记录键盘的按下状态
    private boolean bL,bR,bU,bD;
    // tank是否在移动
    private boolean moving = false;
    // 使用枚举 区分敌我
    private Group group;
    // 标志tank是否死亡
    private boolean live = true; //存活中
    // 开火策略
    FireStrategy fireStrategy = null;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Player(int x, int y, Direction dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;          // 初始化移动方向，默认R
        this.group = group;      // 确定好坏
        this.initFireStrategy(); // 开火策略
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

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
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
    }

    /**
     * paint、keyPressed两种方法的核心思想就是，将操作物给对象自己去处理（键盘对象给tank，tank自己判断加减；画笔给tank，tank自己绘制位置）
     */
    // 自带绘制方法（根据自己的i位置信息绘制）
    public void paint(Graphics g) {
        if(!this.isLive()) return; //若死亡不再绘制
        //使用图片代替方块
        switch (dir){
            case L:
                g.drawImage(ResourceMgr.goodTankL, x, y ,null);
                break;
            case R:
                g.drawImage(ResourceMgr.goodTankR, x, y ,null);
                break;
            case U:
                g.drawImage(ResourceMgr.goodTankU, x, y ,null);
                break;
            case D:
                g.drawImage(ResourceMgr.goodTankD, x, y ,null);
                break;
        }
        this.move();//移动
    }

    // 根据按键移动
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int keyCoder = 0;
        if(keyCode==KeyEvent.VK_LEFT || keyCode==KeyEvent.VK_A){
            keyCoder = 1;
        }else if(keyCode==KeyEvent.VK_RIGHT || keyCode==KeyEvent.VK_D){
            keyCoder = 2;
        }else if(keyCode==KeyEvent.VK_UP || keyCode==KeyEvent.VK_W) {
            keyCoder = 3;
        }else if(keyCode==KeyEvent.VK_DOWN || keyCode==KeyEvent.VK_S){
            keyCoder = 4;
        }

        switch (keyCoder){
            case 1:
                this.bL = true;
                break;
            case 2:
                this.bR = true;
                break;
            case 3:
                this.bU = true;
                break;
            case 4:
                this.bD = true;
                break;
        }
        // 计算最终方向
        setMainDir();
    }

    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int keyCoder = 0;
        if(keyCode==KeyEvent.VK_LEFT || keyCode==KeyEvent.VK_A){
            keyCoder = 1;
        }else if(keyCode==KeyEvent.VK_RIGHT || keyCode==KeyEvent.VK_D){
            keyCoder = 2;
        }else if(keyCode==KeyEvent.VK_UP || keyCode==KeyEvent.VK_W) {
            keyCoder = 3;
        }else if(keyCode==KeyEvent.VK_DOWN || keyCode==KeyEvent.VK_S){
            keyCoder = 4;
        }else if(keyCode==KeyEvent.VK_CONTROL){
            keyCoder = 5;
        }

        switch (keyCoder){
            case 1:
                this.bL = false;
                break;
            case 2:
                this.bR = false;
                break;
            case 3:
                this.bU = false;
                break;
            case 4:
                this.bD = false;
                break;
            case 5:
                this.fire();
                break;
        }

        setMainDir();
    }

    private void initFireStrategy(){
//        int bulletX = x + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
//        int bulletY = y + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
//        // 使用tank的参数去创建一个子弹
//        Bullet bullet = new Bullet(bulletX, bulletY, dir, group);
//        TankFrame.SINGLE_FRAME.add(bullet);
        ClassLoader loader = Player.class.getClassLoader();
        String className = PropertyMgr.get("tankFireStrategy");//策略类名
        try {
            Class clazz = loader.loadClass("com.licretey.tank.strategy." + className);//参数为全路径名
//            Class clazz = Class.forName("com.licretey.tank.strategy." + className);//方式2加载class
            Constructor declaredConstructor = clazz.getDeclaredConstructor(); //拿到构造方法
            fireStrategy = (FireStrategy) declaredConstructor.newInstance();//通过构造方法new一个对象
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 开火
    private void fire() {
        fireStrategy.fire(this);
    }

    private void setMainDir() {
        if(!bL && !bR && !bU && !bD){
            this.moving = false;
        }else {
            this.moving = true;
            if(bL && !bR && !bU && !bD){
                this.dir = Direction.L;
            }
            if(!bL && bR && !bU && !bD){
                this.dir = Direction.R;
            }
            if(!bL && !bR && bU && !bD){
                this.dir = Direction.U;
            }
            if(!bL && !bR && !bU && bD){
                this.dir = Direction.D;
            }
        }
    }

    public void die() {
        this.setLive(false);
    }
}

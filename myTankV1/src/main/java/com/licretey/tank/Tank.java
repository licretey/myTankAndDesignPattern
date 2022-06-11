package com.licretey.tank;

import com.licretey.tank.net.msg.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * 面向对象：
 *      抽象出名词：属性、类
 *      抽象出动词：方法
 */
public class Tank extends AbstactGameObject{
    // 位置信息
    private int x , y;
    // 速度
    private static final int SPEED = 5;
    // 方向
    private Direction dir = Direction.R;
    // 几个变量，用于记录键盘的按下状态
    private boolean bL,bR,bU,bD;
    // enmey默认在移动
    private boolean moving = true;
    // 使用枚举 区分敌我
    private Group group;
    // 标志tank是否死亡
    private boolean live = true;
    // 记录越界前的位置
    private int oldX, oldY;
    // tank图片宽度高度
    private int width, height;
    // tank图片形状
    private Rectangle rect;
    // id
    private UUID id;


    public Tank(TankJoinMsg tankJoinMsg) {
        this.x = tankJoinMsg.getX();
        this.y = tankJoinMsg.getY();
        this.moving = tankJoinMsg.isMoving();
        this.dir = tankJoinMsg.getDir();
        this.group = tankJoinMsg.getGroup();
        this.id = tankJoinMsg.getId();

        this.oldX = x;
        this.oldY = y;
        this.width = ResourceMgr.badTankU.getWidth();
        this.height = ResourceMgr.badTankU.getHeight();
        this.rect = new Rectangle(x,y,width,height);
    }

    public UUID getId() {
        return id;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

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

    public Rectangle getRect() {
        return rect;
    }

    public Tank(int x, int y, Direction dir, Group group){
        this.x = x;
        this.y = y;
        this.dir = dir;     // 初始化移动方向，默认R
        this.group = group; // 确定好坏
        this.oldX = x;
        this.oldY = y;
        this.width = ResourceMgr.badTankU.getWidth();
        this.height = ResourceMgr.badTankU.getHeight();
        this.rect = new Rectangle(x,y,width,height);
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
        oldX = x; // 记录上一个位置
        oldY = y;
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
        boundsCheck(); //边界检查

        /*
        // 随机情况中符合特定条件触发更新方向（降低换向频率）
        if(random.nextInt(100)>96) {
            // 更新敌人tank随机前进方向
            this.dir = Direction.randomDir();
            fire();
        }*/
    }
    private Random random = new Random();

    /**
     * paint、keyPressed两种方法的核心思想就是，将操作物给对象自己去处理（键盘对象给tank，tank自己判断加减；画笔给tank，tank自己绘制位置）
     */
    // 自带绘制方法（根据自己的i位置信息绘制）
    public void paint(Graphics g) {
        if(!this.isLive()) return; //若死亡不再绘制
        //使用图片代替方块
        switch (dir){
            case L:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankL:ResourceMgr.goodTankL, x, y ,null);
                break;
            case R:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankR:ResourceMgr.goodTankR, x, y ,null);
                break;
            case U:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankU:ResourceMgr.goodTankU, x, y ,null);
                break;
            case D:
                g.drawImage(this.group.equals(Group.BAD)?ResourceMgr.badTankD:ResourceMgr.goodTankD, x, y ,null);
                break;
        }
        this.move();//移动
        //更新方框位置
        rect.x = x;
        rect.y = y;
    }

    // 边界检查
    private void boundsCheck() {
        if(x<0 || y<30 || x+width>TankFrame.GAME_WIDTH || y+height>TankFrame.GAME_HEIGHT){
            this.back();
        }
    }

    // 回到上一个位置
    public void back() {
        x = oldX;
        y = oldY;
    }

    // 开火
    private void fire() {
        int bulletX = x + this.width/2 - ResourceMgr.bulletU.getWidth()/2;
        int bulletY = y + this.height/2 - ResourceMgr.bulletU.getHeight()/2;
        // 使用tank的参数去创建一个子弹
        Bullet bullet = new Bullet(this.id,bulletX, bulletY, dir, group);
        TankFrame.SINGLE_FRAME.getGm().add(bullet);
    }

    public void die() {
        this.setLive(false);
    }
}

package com.licretey.tank;

import java.awt.*;

/*
 * 子弹类
 */
public class Bullet extends AbstactGameObject{
    private int x, y;                    // 位置
    private Direction dir;               // 方向
    private Group group;                 // 好坏
    private static final int SPEED = 7;  // 速度
    private boolean live = true;         // 子弹是否从列表中删除
    private int w = ResourceMgr.bulletU.getWidth(); // 子弹宽
    private int h = ResourceMgr.bulletU.getHeight(); // 子弹高
    private Rectangle rect;              // 子弹方块大小

    public Bullet(int x, int y, Direction dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rect = new Rectangle(x,y,w,h);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
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

        rect.x = x;    //更新子弹rectangle
        rect.y = y;    //更新子弹rectangle
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
        boundsCheck(); //边界检查
    }

    // 边界检查
    private void boundsCheck() {
        if(x<0||y<30||x>TankFrame.GAME_WIDTH || y>TankFrame.GAME_HEIGHT){
            this.live = false;
        }
    }


    public void addBomb(){
        this.setLive(false);
        Exploade exploade = new Exploade(x, y);
        TankFrame.SINGLE_FRAME.add(exploade);
    }

    // 简易碰撞检查:
    public void collidesWithTank(Tank tank){

    }

    public Rectangle getRect() {
        return rect;
    }

    public void collidesWithTank(Player player){
        if(!this.isLive() || !player.isLive()) return; // 子弹未死且不与死亡的tank进行检查
        if(this.group == player.getGroup()) return; // 相同阵营子弹对tank无效
        Rectangle rect = new Rectangle(x,y,
                ResourceMgr.bulletU.getWidth(),ResourceMgr.bulletU.getHeight());
        Rectangle rectTank = new Rectangle(player.getX(),player.getY(),
                ResourceMgr.goodTankU.getWidth(),ResourceMgr.goodTankU.getHeight());
        //判断是否相交
        if(rect.intersects(rectTank)){
            player.die();
            addBomb();
            System.out.println(this.x+" "+this.y);
        }
    }

    public void die(){
        this.setLive(false);
    }

}

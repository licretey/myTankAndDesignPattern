package com.licretey.tank;

import com.licretey.tank.chainOfResponsibility.ColliderChain;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Serializable {
    private Player player;                              // tank对象
    //    private List<Bullet> bullets;                // 子弹容器
    //    private List<Tank> enmeys;                   // 敌人容器
    //    private List<Exploade> exploades;            // 爆炸容器
    List<AbstactGameObject> objects;             //抽象对象容器（代替上面三种容器）
    // 碰撞器
    //    Collider collider1 = new BulletTankCollider();
    //    Collider collider2 = new BulletWallCollider();
    // 封装到责任链的list中
    // List<Collider> colliders ;
    ColliderChain colliderChain = new ColliderChain();


    public GameModel(){
        this.initGamreObjects();
    }

    // 初始化游戏对象
    private void initGamreObjects() {
        objects = new ArrayList<>();
        // 抽象到tank类中
        this.player = new Player(100,100, Direction.D, Group.GOOD);
        int enemyTankSize = Integer.parseInt(PropertyMgr.get("initEnemyTankCount"));
        for (int i = 0; i < enemyTankSize; i++) {
            Tank enmey = new Tank(300+80*i, 300, Direction.D, Group.BAD);
            objects.add(enmey);
        }

        //添加1堵墙
        this.add(new Wall(300,150,400,50));
    }


    public void add(AbstactGameObject ago){
        objects.add(ago);
    }

    public void paint(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("bullets:"+bullets.size(),10,50);
//        g.drawString("enmeys:"+enmeys.size(),10,60);
        g.setColor(color); //切回原来的画笔颜色


        if(player.isLive()){
            player.paint(g);//x，y由局部变量抽出到一个对象中，这个对象自己去绘制
        }

        //将死亡对象移除
        for (int i = 0; i < objects.size(); i++) {
            //先判断死活
            if(!objects.get(i).isLive()){
                objects.remove(i);
                break;
            }
        }
        for (int i = 0; i < objects.size(); i++) {
            // 两个物体的碰撞检测
            AbstactGameObject ago1 = objects.get(i);
            for(int j=0; j< objects.size(); j++){
                AbstactGameObject ago2 = objects.get(j);
                colliderChain.collide(ago1,ago2);
            }
            objects.get(i).paint(g);
        }
    }

    public Player getPlayer(){
        return player;
    }
}

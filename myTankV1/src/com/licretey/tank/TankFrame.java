package com.licretey.tank;

import com.licretey.tank.chainOfResponsibility.BulletTankCollider;
import com.licretey.tank.chainOfResponsibility.BulletWallCollider;
import com.licretey.tank.chainOfResponsibility.Collider;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TankFrame extends Frame {
    Player player;                              // tank对象
    //    private List<Bullet> bullets;                // 子弹容器
    //    private List<Tank> enmeys;                   // 敌人容器
    //    private List<Exploade> exploades;            // 爆炸容器
    List<AbstactGameObject> objects;             //抽象对象容器（代替上面三种容器）
    public static final int GAME_WIDTH = 1000;   // 界面宽
    public static final int GAME_HEIGHT = 800;   // 界面高
    // 单例模式创建frame
    public static final TankFrame SINGLE_FRAME = new TankFrame();
    // 碰撞器
    //    Collider collider1 = new BulletTankCollider();
    //    Collider collider2 = new BulletWallCollider();
    List<Collider> colliders ;

    // 单例模式创建frame
    private TankFrame(){
        this.setTitle("tank war");
        this.setLocation(400,100); // 相对于屏幕window
        this.setSize(GAME_WIDTH,GAME_HEIGHT);

        // 添加键盘监听
        this.addKeyListener(new TankKeyListener());
        this.initGamreObjects();
        this.initColliders();
    }

    // 初始化游戏对象
    private void initGamreObjects() {
        objects = new ArrayList<>();
        // 抽象到tank类中
        this.player = new Player(100,100, Direction.D, Group.GOOD);
        int enemyTankSize = Integer.parseInt(PropertyMgr.get("initEnemyTankCount"));
        for (int i = 0; i < enemyTankSize; i++) {
            Tank enmey = new Tank(300+50*i, 200, Direction.D, Group.BAD);
            objects.add(enmey);
        }

        //添加1堵墙
        this.add(new Wall(300,200,400,50));
    }

    //读取配置文件获取碰撞策略，天机到碰撞检测列表种
    private void initColliders(){
        colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        for(String name : colliderNames){
            try {
                Class clazz= Class.forName("com.licretey.tank.chainOfResponsibility." + name);
                Collider collider = (Collider)clazz.getConstructor().newInstance();
                colliders.add(collider);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//        = Arrays.asList(new BulletTankCollider(),new BulletWallCollider());
    }
    // awt自动调用
    // Graphics由系统提供
    @Override
    public void paint(Graphics g) {
        Color color = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("bullets:"+bullets.size(),10,50);
//        g.drawString("enmeys:"+enmeys.size(),10,60);
        g.setColor(color); //切回原来的画笔颜色

        // 绘制方块（x,y相对于窗口）
        // 让这个方块动起来，就需要传入动态的x ，y坐标，并且不停的调用paint绘制（如下）
        //        g.fillRect(x,y,50,50);
        if(player.isLive()){
            player.paint(g);//x，y由局部变量抽出到一个对象中，这个对象自己去绘制
        }
        for (int i = 0; i < objects.size(); i++) {
            //先判断死活
            if(!objects.get(i).isLive()){
                objects.remove(i);
                break;
            }

            AbstactGameObject ago1 = objects.get(i);
            for(int j=0; j< objects.size(); j++){
                AbstactGameObject ago2 = objects.get(j);
                for(Collider collider : colliders){
                    collider.collide(ago1,ago2);
                }
            }
            objects.get(i).paint(g);
        }

        /*
        // 绘制敌人
        for (int i = 0; i < enmeys.size(); i++) {
            // 删除死亡tank
            if(!enmeys.get(i).isLive()){
                enmeys.remove(i);
            }else {
                enmeys.get(i).paint(g);
            }
        }
        // 绘制子弹
        for (int i=0;i<bullets.size();i++){
            // 多个tank的碰撞检查
            for (int j = 0; j < enmeys.size(); j++) {
                bullets.get(i).collidesWithTank(enmeys.get(j));
//                bullets.get(i).collidesWithTank(player); // 敌人的子弹检测是否碰到自己
            }
            // 越界删除
            if(!bullets.get(i).isLive()){
                bullets.remove(i);
            }else {
                bullets.get(i).paint(g);
            }
        }
        // 绘制爆炸
        for (int i = 0; i < exploades.size(); i++) {
            if(!exploades.get(i).isLive()){
                exploades.remove(i);
            }else {
                exploades.get(i).paint(g);
            }
        }*/
    }

    public void add(AbstactGameObject ago){
        objects.add(ago);
    }

    private class TankKeyListener extends KeyAdapter {
        // KeyAdapter 以空方法的形式实现许多KeyListener接口的方法
        @Override
        public void keyPressed(KeyEvent e) {
            // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
            if(player.isLive()){
                player.keyPressed(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
            if(player.isLive()){
                player.keyReleased(e);
            }
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

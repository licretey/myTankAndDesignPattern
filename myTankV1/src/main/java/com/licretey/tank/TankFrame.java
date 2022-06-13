package com.licretey.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class TankFrame extends Frame {
    public static final int GAME_WIDTH = 1000;   // 界面宽
    public static final int GAME_HEIGHT = 800;   // 界面高
    // 单例模式创建frame
    public static final TankFrame SINGLE_FRAME = new TankFrame();
    // 抽出来的model集合对象
    private GameModel gm = new GameModel();

    // 单例模式创建frame
    private TankFrame(){
        this.setTitle("tank war");
        this.setLocation(400,100); // 相对于屏幕window
        this.setSize(GAME_WIDTH,GAME_HEIGHT);

        // 添加键盘监听
        this.addKeyListener(new TankKeyListener());
    }


    // awt自动调用
    // Graphics由系统提供
    @Override
    public void paint(Graphics g) {
        gm.paint(g);

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

    public GameModel getGm(){
        return this.gm;
    }

    private class TankKeyListener extends KeyAdapter {
        // KeyAdapter 以空方法的形式实现许多KeyListener接口的方法
        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            //N键时序列化存档
            if(keyCode ==  KeyEvent.VK_N) {
                save();
            }else if(keyCode ==  KeyEvent.VK_L) {
                load();
            }else {
                // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
                Player player = gm.getPlayer();
                if(player.isLive()){
                    player.keyPressed(e);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 如果创建了tank对象，对键盘的监听也交由这个对象自己来完成
            Player player = gm.getPlayer();
            if(player.isLive()){
                player.keyReleased(e);
            }
        }
    }

    private void save(){
        ObjectOutputStream oos = null;
        try {
            File file = new File("D:/myTank.dat");
            FileOutputStream fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            //将对象写到文件中去
            oos.writeObject(gm);
            oos.flush();//写在finally中
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(oos!=null){
                    oos.close();//写在finally中
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void load(){
        ObjectInputStream ois = null;
        try {
            File file = new File("D:/myTank.dat");
            FileInputStream fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            //将对象写到文件中去
            this.gm = (GameModel)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(ois!=null) {
                    ois.close();//写在finally中
                }
            } catch (IOException e) {
                e.printStackTrace();
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

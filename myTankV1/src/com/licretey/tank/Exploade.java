package com.licretey.tank;

import java.awt.*;

public class Exploade {
    private int x, y;                    // 位置
    private int width,height;            // 宽高
    private int step = 0;                // 步数

    public Exploade(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = ResourceMgr.explodes[0].getWidth();
        this.height = ResourceMgr.explodes[0].getHeight();
    }

    // 绘制爆炸的一组图片：记录爆炸的位置，爆炸之后的几帧在爆炸位置处依次绘制爆炸图片
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step],x,y,null);
        step++;
        if(step>=ResourceMgr.explodes.length){
            step = 0;
        }
    }

}

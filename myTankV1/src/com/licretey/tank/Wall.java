package com.licretey.tank;

import java.awt.*;

public class Wall extends AbstactGameObject{
    private int x,y;//位置
    private int w,h;//宽高


    public Wall(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void paint(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x,y,w,h);
        g.setColor(color);
    }
}

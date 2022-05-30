package com.mashibing.tank.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;

import com.mashibing.tank.Audio;
import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.TankFrame;
/**
 * 方形爆炸效果
 */
public class RectExplode extends BaseExplode {
	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	
	private int x,y;
	
//	private boolean isLive = true;
	TankFrame tf = null;
	
	private int step = 0;
	
	public RectExplode(int x,int y,TankFrame tf) {
		this.x = x;
		this.y = y;
		this.tf = tf;
		
		new Thread(()->new Audio("audio/explode.wav").play()).start();;
	}
	
	@Override
	public void paint(Graphics g) {
//		g.drawImage(ResourceMgr.explodes[step++],x,y,null);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x,y,10*step,10*step);
		step++;
		if(step >= 5)
			tf.explodes.remove(this);
		g.setColor(c);
	}
}

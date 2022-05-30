package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.mashibing.tank.abstractfactory.BaseBullet;
import com.mashibing.tank.abstractfactory.BaseTank;

/**
 * 炮弹
 * @author Administrator
 */
public class Bullet extends BaseBullet{

	private static final int speed = 10;
	public static int WIDTH = ResourceMgr.bulletD.getWidth(),HEIGHT = ResourceMgr.bulletD.getHeight();
	
	private int x,y;
	private Dir dir;
	
	Rectangle rect = new Rectangle();
	
	private boolean isLive = true;
	TankFrame tf = null;
	private Group group = Group.BAD;
	
	public Bullet(int x, int y, Dir dir,Group group ,TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
		
		tf.bullets.add(this);
		
	}
	
	public static int getWIDTH() {
		return WIDTH;
	}

	public static void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}


	public static void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
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

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public TankFrame getTf() {
		return tf;
	}

	public void setTf(TankFrame tf) {
		this.tf = tf;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public static int getSpeed() {
		return speed;
	}

	public void paint(Graphics g) {
		if (!isLive) {
			tf.bullets.remove(this);
		}
		
		switch (dir) {
		case LEFT:
			g.drawImage(ResourceMgr.bulletL,x,y,null);
			break;
		case UP:
			g.drawImage(ResourceMgr.bulletU,x,y,null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.bulletR,x,y,null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.bulletD,x,y,null);
			break;

		default:
			break;
		}
		
		move();
	}

	private void move() {
		switch (dir) {
		case LEFT:
			x -= speed;
			break;
		case UP:
			y -= speed;
			break;
		case RIGHT:
			x += speed;
			break;
		case DOWN:
			y += speed;
			break;

		default:
			break;
		}
		
		//更新rect
		rect.x = this.x;
		rect.y = this.y;
		
		if (x<0||y<0||x>TankFrame.GAME_WIDTH||y>TankFrame.GAME_HEIGHT) 
			isLive=false;
	}

	//判断炮弹是否击中坦克
	public void collideWith(BaseTank tank) {
		
		if(this.group == tank.group) return;
		
		if (this.rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			int ex = tank.getX()+Tank.WIDTH/2 - Explode.WIDTH/2;
			int ey = tank.getY()+Tank.HEIGHT/2 -Explode.HEIGHT/2;
			tf.explodes.add(tf.gf.createExplode(ex,ey,tf));
		}
	}

	private void die() {
		this.isLive = false;
	}
	
}

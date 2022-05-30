package com.mashibing.tank;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import com.mashibing.tank.abstractfactory.BaseBullet;
import com.mashibing.tank.abstractfactory.BaseExplode;
import com.mashibing.tank.abstractfactory.BaseTank;
import com.mashibing.tank.abstractfactory.DefaultFactory;
import com.mashibing.tank.abstractfactory.GameFactory;

public class TankFrame extends Frame{

	Tank myTank = new Tank(375, 275, Dir.DOWN,Group.GOOD,this);
	public List<BaseBullet> bullets = new ArrayList<>();
	public List<BaseTank> tanks = new ArrayList<>();
	public List<BaseExplode> explodes = new ArrayList<>();

	
	public GameFactory gf = DefaultFactory.getInstance();
	
	public static final int GAME_WIDTH=1080 , GAME_HEIGHT=960;
	
	public TankFrame() {
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setResizable(false);
		setTitle("Tank war");
		setVisible(true);
		
		//添加键盘监听
		addKeyListener(new MyKeyListener());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.black);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage,0,0,null);
	}
	
	//窗口重新绘制的时候会调用该方法
	@Override
	public void paint(/*画笔*/Graphics g) {
		
		Color c = g.getColor();
		g.setColor(Color.white);
		g.drawString("子弹的数量"+bullets.size(), 10, 60);
		g.drawString("敌方坦克数量"+tanks.size(), 10, 80);
		g.drawString("爆炸效果数量"+explodes.size(), 10, 100);
		g.setColor(c);
		//己方坦克
		myTank.paint(g);
		//坦克子弹
//		for(Bullet bullet : bullets) //这种方法遍历时不能删除集合中的元素
		for (int i = 0; i < bullets.size(); i++) //这种方法遍历时可以删除集合中的元素不会发生异常 
			bullets.get(i).paint(g);
		
//		for (Iterator<Bullet> iterator = bullets.iterator(); iterator.hasNext();) {
//			Bullet bullet = iterator.next();
//			if (!bullet.isLive) iterator.remove();
//		}
		
		//敌方坦克
		for (int i = 0; i < tanks.size(); i++)
			tanks.get(i).paint(g);
		
		//爆炸效果图
				for(int i=0 ;i<explodes.size();i++)
					explodes.get(i).paint(g);
		
		//判断炮弹是否打到坦克上面
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < tanks.size(); j++) {
				bullets.get(i).collideWith(tanks.get(j));
			}
		}
		
	}
	
	//键盘监听处理类
	class MyKeyListener extends KeyAdapter{
		boolean bL = false;
		boolean bU = false;
		boolean bR = false;
		boolean bD = false;
		//键被按下的时候调用
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = true;
				break;
			case KeyEvent.VK_UP:
				bU = true;
				break;
			case KeyEvent.VK_RIGHT:
				bR = true;
				break;
			case KeyEvent.VK_DOWN:
				bD = true;
				break;

			default:
				break;
			}
			setMainTankDir();
		}
		
		//键被抬起来的时候调用
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_LEFT:
				bL = false;
				break;
			case KeyEvent.VK_UP:
				bU = false;
				break;
			case KeyEvent.VK_RIGHT:
				bR = false;
				break;
			case KeyEvent.VK_DOWN:
				bD = false;
				break;
			case KeyEvent.VK_CONTROL:
				myTank.fire();
				break;
			
			default:
				break;
			}
			setMainTankDir();
		}

		//设置主战坦克的方向
		private void setMainTankDir() {
			myTank.setMoving(true);
			if (bL) myTank.setDir(Dir.LEFT);
			if (bU) myTank.setDir(Dir.UP);
			if (bR) myTank.setDir(Dir.RIGHT);
			if (bD) myTank.setDir(Dir.DOWN);
			
			if (!bL && !bU && !bR && !bD) myTank.setMoving(false);
		}
		
	}
}

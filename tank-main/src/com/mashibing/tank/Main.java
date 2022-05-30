package com.mashibing.tank;

public class Main {

	public static void main(String[] args) {
		TankFrame tf = new TankFrame();
		
		int tankCount = Integer.parseInt((String)PropertyMgr.getValue("initTankCount"));
		
		for (int i = 0; i < tankCount; i++) {
			tf.tanks.add(tf.gf.createTank(50+50*i, 200, Dir.DOWN,Group.BAD, tf));
		}
		
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tf.repaint();
		}
	}
	
}

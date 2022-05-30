package com.mashibing.tank.abstractfactory;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Group;
import com.mashibing.tank.TankFrame;
/**
 * 方形工厂 
 */
public class RectFactory extends GameFactory {

	private RectFactory() {}
	private static RectFactory INSTANCE = new RectFactory();
	public static RectFactory getInstance (){
		return INSTANCE;
	}
	
	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new RectTank(x,y,dir,group,tf);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new RectBullet(x,y,dir,group,tf);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame tf) {
		return new RectExplode(x,y,tf);
	}

}

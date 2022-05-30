package com.licretey.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 资源管理器,把图片内容都加载进内存
 * @author Administrator
 */
public class ResourceMgr {
	public static BufferedImage goodTankL,goodTankR,goodTankU,goodTankD;	// 好坦克各个方向图片资源
	public static BufferedImage badTankL,badTankR,badTankU,badTankD;		// 坏坦克各个方向图片资源
	public static BufferedImage bulletL,bulletR,bulletU,bulletD;			// 子弹各个方向图片资源
	public static BufferedImage[] explodes = new BufferedImage[16];

	// 资源不应该反复读取磁盘加载，利用static仅读取一次即可
	static {
		try {
			goodTankU =  ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png"));
			goodTankR =  ImageUtil.rotateImage(goodTankU, 90);
			goodTankD =  ImageUtil.rotateImage(goodTankR, 90);
			goodTankL =  ImageUtil.rotateImage(goodTankD, 90);
			
			badTankU =  ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png"));
			badTankR =  ImageUtil.rotateImage(badTankU, 90);
			badTankD =  ImageUtil.rotateImage(badTankR, 90);
			badTankL =  ImageUtil.rotateImage(badTankD, 90);

			// 子弹图片
			bulletU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
			bulletR = ImageUtil.rotateImage(bulletU, 90);
			bulletD = ImageUtil.rotateImage(bulletR, 90);
			bulletL = ImageUtil.rotateImage(bulletD, 90);

			// 爆炸图片
			for(int i=0; i<16; i++) {
				explodes[i] = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif"));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

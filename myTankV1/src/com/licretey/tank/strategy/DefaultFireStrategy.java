package com.licretey.tank.strategy;

import com.licretey.tank.*;
import com.licretey.tank.net.TankClient;
import com.licretey.tank.net.msg.BulletNewMsg;

public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Player player) {
        int bulletX = player.getX() + ResourceMgr.goodTankU.getWidth()/2 - ResourceMgr.bulletU.getWidth()/2;
        int bulletY = player.getY() + ResourceMgr.goodTankU.getHeight()/2 - ResourceMgr.bulletU.getHeight()/2;
        // 使用tank的参数去创建一个子弹
        Bullet bullet = new Bullet(player.getId(), bulletX, bulletY, player.getDir(), player.getGroup());
        TankFrame.SINGLE_FRAME.getGm().add(bullet);

        //发送消息
        TankClient.INSTANCE.send(new BulletNewMsg(bullet));
    }
}

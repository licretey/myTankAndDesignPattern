package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;
import com.licretey.tank.Bullet;
import com.licretey.tank.ResourceMgr;
import com.licretey.tank.Tank;
import com.licretey.tank.net.TankClient;
import com.licretey.tank.net.msg.TankDieMsg;

import java.awt.*;

public class BulletTankCollider implements Collider{

    @Override
    public boolean collide(AbstactGameObject ago1, AbstactGameObject ago2) {
        if(ago1 instanceof Bullet && ago2 instanceof Tank){
            Bullet bullet = (Bullet) ago1;
            Tank tank = (Tank) ago2;

            if(!bullet.isLive() || !tank.isLive()) return false;   // 子弹未死且不与死亡的tank进行检查
            if(bullet.getGroup() == tank.getGroup()) return true; // 相同阵营子弹对tank无效
            Rectangle rectTank = tank.getRect();
            //判断是否相交
            if(bullet.getRect().intersects(rectTank)){
                tank.die();
                bullet.addBomb();

                //通知客户端tank与bullet死亡
                TankClient.INSTANCE.send(new TankDieMsg(tank.getId(),bullet.getId()));
                return false;
            }
        }

        if(ago1 instanceof Tank && ago2 instanceof Bullet){
            return this.collide(ago2,ago1);
        }
        return true;
    }
}

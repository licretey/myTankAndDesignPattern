package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;
import com.licretey.tank.Bullet;
import com.licretey.tank.Tank;

import java.awt.*;

public class TankTankCollider implements Collider{

    @Override
    public boolean collide(AbstactGameObject ago1, AbstactGameObject ago2) {
        // ag1可能和ago2是同一个而无法移动
        if( ago1 != ago2 && ago1 instanceof Tank && ago2 instanceof Tank){
            Tank tank1 = (Tank) ago1;
            Tank tank2 = (Tank) ago2;

            if(tank1.isLive() && tank2.isLive()) {
                //判断是否相交
                if(tank1.getRect().intersects(tank2.getRect())){
                    tank1.back();
                    tank2.back();
                }
            }
        }
        return true;
    }
}

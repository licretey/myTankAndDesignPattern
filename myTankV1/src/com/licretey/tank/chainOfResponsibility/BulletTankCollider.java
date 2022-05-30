package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;
import com.licretey.tank.Bullet;
import com.licretey.tank.Tank;

public class BulletTankCollider implements Collider{

    @Override
    public void collide(AbstactGameObject ago1, AbstactGameObject ago2) {
        if(ago1 instanceof Bullet && ago2 instanceof Tank){
            Bullet bullet = (Bullet) ago1;
            Tank tank = (Tank) ago2;
            bullet.collidesWithTank(tank);
        }

        if(ago1 instanceof Tank && ago2 instanceof Bullet){
            Bullet bullet = (Bullet) ago2;
            Tank tank = (Tank) ago1;
            bullet.collidesWithTank(tank);
        }
    }
}

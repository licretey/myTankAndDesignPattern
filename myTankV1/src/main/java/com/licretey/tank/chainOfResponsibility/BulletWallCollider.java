package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;
import com.licretey.tank.Bullet;
import com.licretey.tank.Wall;

public class BulletWallCollider implements Collider{

    @Override
    public boolean collide(AbstactGameObject ago1, AbstactGameObject ago2) {
        if(ago1 instanceof Bullet && ago2 instanceof Wall){
            Bullet bullet = (Bullet) ago1;
            Wall wall = (Wall) ago2;
            if(bullet.isLive()){
                if(bullet.getRect().intersects(wall.getRect())){
                    bullet.die();
                    return false;
                }
            }
        }

        if(ago1 instanceof Wall && ago2 instanceof Bullet){
            return this.collide(ago2,ago1);    //递归
        }

        return true;
    }
}

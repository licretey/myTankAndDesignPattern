package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;
import com.licretey.tank.Tank;
import com.licretey.tank.Wall;

public class TankWallCollider implements Collider{

    @Override
    public boolean collide(AbstactGameObject ago1, AbstactGameObject ago2) {
        if(ago1 instanceof Tank && ago2 instanceof Wall){
            Tank tank = (Tank) ago1;
            Wall wall = (Wall) ago2;
            if(tank.isLive()){
                if(tank.getRect().intersects(wall.getRect())){
                    tank.back();
                }
            }
        }

        if(ago1 instanceof Wall && ago2 instanceof Tank){
            this.collide(ago2,ago1);    //递归
        }

        return true;
    }
}

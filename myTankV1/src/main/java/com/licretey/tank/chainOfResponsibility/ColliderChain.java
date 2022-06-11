package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;
import com.licretey.tank.PropertyMgr;

import java.util.ArrayList;
import java.util.List;

public class ColliderChain implements Collider{
    List<Collider> colliders;

    public ColliderChain(){
        initColliders();
    }

    //读取配置文件获取碰撞策略，天机到碰撞检测列表种
    private void initColliders(){
        colliders = new ArrayList<>();
        String[] colliderNames = PropertyMgr.get("colliders").split(",");
        for(String name : colliderNames){
            try {
                Class clazz= Class.forName("com.licretey.tank.chainOfResponsibility." + name);
                Collider collider = (Collider)clazz.getConstructor().newInstance();
                colliders.add(collider);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 链式依次处理
    public boolean collide(AbstactGameObject ago1,AbstactGameObject ago2){
        for(Collider collider : colliders){
            if(!collider.collide(ago1,ago2)){
                //碰撞返回false时，后续碰撞不必继续检测
//                break;
                return false;
            }
        }
        return true;
    }
}

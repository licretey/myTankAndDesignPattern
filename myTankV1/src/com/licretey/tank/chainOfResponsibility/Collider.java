package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;

// 责任链模式；碰撞器
public interface Collider {
    public void collide(AbstactGameObject ago1,AbstactGameObject ago2);
}

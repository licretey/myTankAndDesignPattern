package com.licretey.tank.chainOfResponsibility;

import com.licretey.tank.AbstactGameObject;

// 责任链模式；碰撞器
public interface Collider {
    /* 在责任链模式中
     * 通过返回的boolean值，判断是否继续执行下一个责任策略
     * true 继续，false停止
     */
    public boolean collide(AbstactGameObject ago1,AbstactGameObject ago2);

}

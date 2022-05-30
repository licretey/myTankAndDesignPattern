package com.licretey.tank.strategy;

import com.licretey.tank.Player;

import java.io.Serializable;

// 抽象开火策略
public interface FireStrategy extends Serializable {
    public void fire(Player player);
}

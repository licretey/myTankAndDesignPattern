package com.licretey.tank.strategy;

import com.licretey.tank.Player;
import com.licretey.tank.Tank;

// 抽象开火策略
public interface FireStrategy {
    public void fire(Player player);
}

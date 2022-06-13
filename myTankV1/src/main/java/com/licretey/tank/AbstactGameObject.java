package com.licretey.tank;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstactGameObject implements Serializable {
    //抽象类中的属性越少越好
    public abstract void paint(Graphics graphics);
    //是否存活
    public abstract boolean isLive();
}

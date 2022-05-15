package com.licretey.tank;

import java.util.Random;

public enum Direction {
    // 使用int类型，不能阻止任何人去赋值，而赋值的时候可能会赋上枚举值之外的值（如只有0，1，赋值2表示什么呢），枚举会限制仅那几个值
    L, U, R, D;

    private static Random random = new Random();
    // 获取一个随机方向
    public static Direction randomDir(){
        int nextIndex = random.nextInt(values().length); // values() 获取枚举中的所有元素
        return values()[nextIndex];
    }
}

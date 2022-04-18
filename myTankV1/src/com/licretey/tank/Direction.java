package com.licretey.tank;

public enum Direction {
    // 使用int类型，不能阻止任何人去赋值，而赋值的时候可能会赋上枚举值之外的值（如只有0，1，赋值2表示什么呢），枚举会限制仅那几个值
    L, U, R, D
}

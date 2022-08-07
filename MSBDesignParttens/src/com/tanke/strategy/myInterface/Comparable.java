package com.tanke.strategy.myInterface;


// 使用Object类型时可能出现类型转换异常
//public interface Comparable {
//    int compareTo(Object o);
//}

// 使用泛型处理
public interface Comparable<T> {
    int compareTo(T o); // interface中默认public
}

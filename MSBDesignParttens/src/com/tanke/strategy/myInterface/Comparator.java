package com.tanke.strategy.myInterface;

@FunctionalInterface
// 声明为函数式接口，如果接口中只有一个方法不写此注解也可
public interface Comparator<T> {
    int compare(T o1, T o2);
//    // 1.8 之后可以直接在interface中写方法的实现
//    default void m() {
//        System.out.println("m");
//    }
}

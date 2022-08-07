package com.tanke.singleton;


/*
    静态内部类方式：JVM保证单例（保证多线程安全：JVM加载一个class时保证只加载一次）
    实现：加载外部类时不会加载内部类，由接口实现懒加载
 */
public class Mgr07 {

    private Mgr07(){}; // 关键：构造方法为private，其他人便无法new创建

    // 静态内部类
    private static class Mgr07Holder{
        private final static Mgr07 INSTANCE = new Mgr07();
    }

    // 在更小的代码块中枷锁初始化
    private static Mgr07 getInstance(){
        return Mgr07Holder.INSTANCE; // 控制加载时机，无则由内部类生成，后续再调会直接使用（内部类中是静态的）
    }

    // 多线程测试
    public static void main(String[] args) {
        // 因为枷锁而获取到同一对象
        for (int i = 0; i <100; i++) {
            new Thread(()->{
                System.out.println(Mgr07.getInstance().hashCode());
            }).start();
        }

    }

    public void m() {
        System.out.println("m");
    }
}

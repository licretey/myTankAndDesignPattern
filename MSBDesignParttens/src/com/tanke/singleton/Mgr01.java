package com.tanke.singleton;


/*
    饿汉模式：推荐
        类加载到内存后，就通过static实例化一个单例，JVM保证线程安全（JVM加载一个class时保证只加载一次）
    唯一缺点：不管用到与否，类加载时就完成实例化（其实不用时不建议装载到内存）

 */
public class Mgr01 {
    private  static final Mgr01 INSTANCE = new Mgr01(); // 定义一个静态实例

    private Mgr01(){}; // 关键：构造方法为private，其他人便无法new创建

    private static Mgr01 getInstance(){ return INSTANCE; }

    public static void main(String[] args) {
        Mgr01 m1 = Mgr01.getInstance(); // 只能这种方式获取Mgr01实例， 使用new Mgr01();是无法获取对象的
        Mgr01 m2 = Mgr01.getInstance();
        System.out.println(m1 == m2); // 结果 m1=m2
    }

    public void m() {
        System.out.println("m");
    }
}

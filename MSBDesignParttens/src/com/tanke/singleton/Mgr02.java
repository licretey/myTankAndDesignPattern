package com.tanke.singleton;


/*
    思路完全同01：（只是使用静态语句块初始化）

 */
public class Mgr02 {
    // 定义一个静态实例
    private  static final Mgr02 INSTANCE ;
    static {
        INSTANCE = new Mgr02(); // INSTANCE是final类型的
    }

    private Mgr02(){}; // 关键：构造方法为private，其他人便无法new创建

    private static Mgr02 getInstance(){ return INSTANCE; }

    public static void main(String[] args) {
        Mgr02 m1 = Mgr02.getInstance(); // 只能这种方式获取Mgr01实例， 使用new Mgr01();是无法获取对象的
        Mgr02 m2 = Mgr02.getInstance();
        System.out.println(m1 == m2); // 结果 m1=m2
    }

    public void m() {
        System.out.println("m");
    }
}

package com.tanke.singleton;


/*
    懒汉式：什么时候用，什么时候初始化
    缺点：多线程时线程不安全

 */
public class Mgr03 {
    // 定义一个静态实例
    private  static Mgr03 INSTANCE ; // 不再是final类型（有final必须初始化）

    private Mgr03(){}; // 关键：构造方法为private，其他人便无法new创建

//    static {
//        INSTANCE = new Mgr03();
//    }
    // 在接口中初始化
    // 第一次调用时，且判断为空才初始化化，不同获取之前初始化的
    private static Mgr03 getInstance(){
        if(INSTANCE == null){
//            // 多线程测试
//            try {
//                Thread.sleep(1);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
           INSTANCE = new Mgr03();
        }
        return INSTANCE;
    }

    // 多线程测试
    public static void main(String[] args) {
        // 创建100个线程
        for (int i = 0; i <100; i++) {
            new Thread(()->{
                System.out.println(Mgr03.getInstance().hashCode());
            }).start();
        }
        // lambada表达式：是对只有一个方法的匿名内部类的匿名接口
        // 一般的多线程线程编写方式：
        // new Thread()，需要传入一个Runable对象，在Runable对象中复写run方法（run方法中为线程逻辑），如下
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //                System.out.println("线程内逻辑");
        //            }
        //        }).start();

    }

    public void m() {
        System.out.println("m");
    }
}

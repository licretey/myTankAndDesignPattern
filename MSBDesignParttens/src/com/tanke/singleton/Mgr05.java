package com.tanke.singleton;


/*
    懒汉式优化：加锁处理线程不安全且效率不降低（下面并不成功，线程不安全）
 */
public class Mgr05 {
    // 定义一个静态实例
    private  static Mgr05 INSTANCE ;

    private Mgr05(){}; // 关键：构造方法为private，其他人便无法new创建

    // 在更小的代码块中枷锁初始化
    private static Mgr05 getInstance(){
        if(INSTANCE == null){
            // 加锁在判断INSTANCE之后，依旧存在运行代码块初始化其它线程拿到空INSTANCE而初始化线程
           synchronized (Mgr05.class){
               // 多线程测试
               try {
                   Thread.sleep(1);
               }catch (InterruptedException e){
                   e.printStackTrace();
               }
               INSTANCE = new Mgr05();
           }// 对代码块枷锁
        }
        return INSTANCE;
    }

    // 多线程测试
    public static void main(String[] args) {
        // 因为枷锁而获取到同一对象
        for (int i = 0; i <100; i++) {
            new Thread(()->{
                System.out.println(Mgr05.getInstance().hashCode());
            }).start();
        }

    }

    public void m() {
        System.out.println("m");
    }
}

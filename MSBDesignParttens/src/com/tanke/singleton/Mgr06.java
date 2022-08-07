package com.tanke.singleton;


/*
    对05懒汉式优化：双重加锁处理线程不安全且效率降低问题
 */
public class Mgr06 {
    // 定义一个静态实例
    private  static volatile Mgr06 INSTANCE ;
    //需要加上volatile，因为虚拟机编译时特别时进行JIT优化时会进行重排操作而直接返回

    private Mgr06(){}; // 关键：构造方法为private，其他人便无法new创建

    // 在更小的代码块中枷锁初始化
    private static Mgr06 getInstance(){
        if(INSTANCE == null){ // 此判断能减少加锁的次数提高效率
           synchronized (Mgr06.class){
               // 再判断一次
               if(INSTANCE == null){
                   try {
                       Thread.sleep(1);
                   }catch (InterruptedException e){
                       e.printStackTrace();
                   }
                   INSTANCE = new Mgr06();
               }
           }// 对代码块枷锁
        }
        return INSTANCE;
    }

    // 多线程测试
    public static void main(String[] args) {
        // 因为枷锁而获取到同一对象
        for (int i = 0; i <100; i++) {
            new Thread(()->{
                System.out.println(Mgr06.getInstance().hashCode());
            }).start();
        }

    }

    public void m() {
        System.out.println("m");
    }
}

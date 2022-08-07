package com.tanke.singleton;


/*
    懒汉式优化：加锁处理线程不安全
    缺点：效率降低
 */
public class Mgr04 {
    // 定义一个静态实例
    private  static Mgr04 INSTANCE ;

    private Mgr04(){}; // 关键：构造方法为private，其他人便无法new创建

    // 在接口中枷锁初始化
    private static synchronized Mgr04 getInstance(){
        if(INSTANCE == null){
            // 多线程测试
            try {
                Thread.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
           INSTANCE = new Mgr04();
        }
        return INSTANCE;
    }

    // 多线程测试
    public static void main(String[] args) {
        // 因为枷锁而获取到同一对象
        for (int i = 0; i <100; i++) {
            new Thread(()->{
                System.out.println(Mgr04.getInstance().hashCode());
            }).start();
        }

    }

    public void m() {
        System.out.println("m");
    }
}

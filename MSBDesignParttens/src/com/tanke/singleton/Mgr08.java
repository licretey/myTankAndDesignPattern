package com.tanke.singleton;


/*
    EffectJava中实现的单例:
      即线程安全，又可防止反序列化

    Java反射：可以通过class文件加载到内存，再new一个实例出来（前面的都可以通过反射来创出多个实例）
    枚举类实现无法反序列化原因：枚举类没有构造方法，即使拿到了class文件并加载了，也无法去创建对象
 */
public enum Mgr08 {

    INSTANCE;

    public void m(){};


    // 多线程测试
    public static void main(String[] args) {
        // 因为枷锁而获取到同一对象
        for (int i = 0; i <100; i++) {
            new Thread(()->{
                System.out.println(Mgr08.INSTANCE.hashCode());
            }).start();
        }

    }
}

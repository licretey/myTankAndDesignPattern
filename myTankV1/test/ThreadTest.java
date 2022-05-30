import org.junit.jupiter.api.Test;

public class ThreadTest {

    @Test
    public void testTread1(){
        //方式1；继承Thread，重写run方法
        new People().start();
    }

    @Test
    public void testTread2(){
        //方式2；继承Runnable
        new Thread(new Human()).start();
    }

    @Test
    public void testTread3(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                System.out.println("匿名内部类启动进程--全貌");
            }
        }).start();
        //方式3；匿名内部内实现
        new Thread(()->{
            //只有一个方面名需要重写，方法名可胜
            System.out.println("匿名内部类启动进程--lambda简写");
        }).start();
    }



}

class People extends Thread{
    @Override
    public void run(){
        System.out.println("People");
    }
}

class Human implements Runnable{
    @Override
    public void run(){
        System.out.println("human");
    }
}

package callable;

import java.util.concurrent.FutureTask;

//FutureTask结合了Future,Callable
public class FutureTaskTest {
    public static void main(String[] args) throws  Exception{
        FutureTask<Long> ft = new FutureTask(new MyTask());//FutureTask 可以自己启动线程
        new Thread(ft).start();
        Long l = ft.get();
        System.out.println(l);
    }
}
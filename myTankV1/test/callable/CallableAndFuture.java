package callable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableAndFuture {
    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Long> future = service.submit(new MyTask());//只允许使用线程池中的线程


        long l = future.get(); //阻塞等待，获取一个结果时可以拿到东西
        System.out.println(l);

        System.out.println("go on!");
    }
}

package callable;

import java.util.concurrent.Callable;

// jdk提供有callable接口（call方法的返回类型通过泛型指定）
public class MyTask implements Callable<Long> {

    @Override
    public Long call() throws Exception {
        long r = 0L;
        for(long i=0L; i<10L; i++) {
            r += i;
            Thread.sleep(500);
            System.out.println(i + " added!");
        }
        return r;
    }
}
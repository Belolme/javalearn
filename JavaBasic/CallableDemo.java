import fj.Unit;
import fj.data.Array;

import java.util.concurrent.*;

/**
 * Created by billin on 16-6-11.
 * A demo for callable
 */
public class CallableDemo {

    static {

        ExecutorService executor = Executors.newCachedThreadPool();
        Future future = executor.submit(new PrintNumberWithReturn());


        if (future.isDone())
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        else
            System.out.println("future is not done");

        executor.shutdown();

    }

    public static void main(String[] args) {

    }


}


class PrintNumberWithReturn implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Array.range(1, 10000000).foreach(i -> {
            System.out.println(i + " " + Thread.currentThread().getName());
            return Unit.unit();
        });
        return 1;
    }
}
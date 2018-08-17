package javax.main.concurrent;

import fj.Unit;
import fj.data.Array;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by billin on 16-6-11.
 * A demo for ThreadFactory
 */
public class ThreadFactoryDemo {


    static {
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Start a not daemon thread ");
        executor.execute(new NumberCounter());
        executor.shutdown();
    }

    static {
        System.out.println("Start a daemon thread");
        ExecutorService executor = Executors.newCachedThreadPool(new MyThreadFactory());
        executor.execute(new NumberCounter());
        executor.shutdown();
    }

    public static void main(String[] args) {

    }
}


class MyThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(new NumberCounter());
        thread.setDaemon(true);
        return thread;
    }
}

class NumberCounter implements Runnable{

    @Override
    public void run() {
        Array.range(0,100).foreach(i -> {
            System.out.println(i + " " + Thread.currentThread().getName() );
            return Unit.unit();
        });
    }
}
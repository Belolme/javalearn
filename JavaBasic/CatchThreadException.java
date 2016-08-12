import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by billin on 16-6-11.
 * A demo for catching exception in a thread
 */
public class CatchThreadException {

    static {
        ExecutorService executor = Executors.newSingleThreadExecutor(new CatchExceptionThreadFactory());
        executor.execute(new ThreadWithException());
        executor.shutdown();
    }

    public static void main(String[] args) {

    }

}

class CatchExceptionThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(new ThreadWithException());
        thread.setUncaughtExceptionHandler(new ExceptionResulotion());

        return thread;
    }
}


class ExceptionResulotion implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(e.toString());
    }
}

class ThreadWithException implements Runnable{

    @Override
    public void run() {

        for(int i=0;i<10000;i++){
            Math.pow(i, i);
        }
        throw new RuntimeException();
    }
}

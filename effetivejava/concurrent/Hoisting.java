package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * 一个因为 Hosting 优化导致的多线程问题
 * <p/>
 * Created by Billin on 2017/3/1.
 */
public class Hoisting {

    private static boolean done;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            int i = 0;
            while (!done) {
                i++;
            }
        }).start();

        long start;
        System.out.println(start = System.currentTimeMillis());
        TimeUnit.SECONDS.sleep(1);
        System.out.println(System.currentTimeMillis() - start);
        done = true;

        /* output:
        1488364942251
        1003
        forever loop................................
         */
    }
}

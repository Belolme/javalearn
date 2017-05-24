/**
 * 测试 volatile 的内存语义, 写入的时候把线程的共享变量都写到内存中，读入时把
 * 线程的共享变量都置为 invalidate
 * <p/>
 * Created by Billin on 2017/5/24.
 */
public class VolatileTest {

    volatile private int a = 0;

    private int b = 0;

    private int c = 0;

    private boolean flag = false;

    private void test() {
        Thread thread = new Thread(() -> {
            boolean tmp = flag;
            while (!tmp) {
                // 解决无限死循环的另外一个方法就是把下面这句代码放在 synchronized 关键字下
                // 利用 synchronized 的内存语义阻止重排序的发生
//                synchronized (this) {
                tmp = flag;
//                }

                // 如果不添加下面那一句代码，将会无限死循环
                // 添加了则不会出现死循环的问题，这间接证明了 volatile
                // 确实是清空了当前工作线程的所有线程缓存，阻止了重排序的发生
//                int temp = a;
            }

            int tempA = a;
            int tempB = b;
            int tempC = c;

            if (tempA != tempB || tempA != tempC) {
                System.out.println(tempA + " " + tempB + " " + tempC);
            }
        });

        new Thread(() -> {
            thread.start();
            for (int i = 0; i < 100000000; i++) {
                a += 1;
                b += 1;
                c += 1;
            }

            flag = true;
        }).start();
    }

    public static void main(String[] args) {
        VolatileTest test = new VolatileTest();
        test.test();
    }
}

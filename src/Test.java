/**
 * Created by Billin on 2016/6/16.
 * Just for test
 */
public class Test {

    int v1 = 11;
    int v2 = 5;
    volatile int a;

    int i;
    int j;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    private void test2() {
        setA(222222);
        int temp = getA();
        if (temp != 222222)
            System.out.println(temp);
        setA(1);
    }

    private void test() {
        i = v1; // 1 volatile R, W
        j = v2; // 2 volatile R, W
        v2 = j; // 5 volatile W, R
        v1 = i; // 4 volatile W, R
        a = i;  // 3          R, W

        System.out.println(a);
        System.out.println(v1);
        System.out.println(v2);
    }

    public static void main(String[] args) {
        Test test = new Test();
        new Thread(() -> {
            System.out.println("thread 1 start");
            for (long i = 0; i < 100000L; i++) {
                test.test2();
            }
            System.out.println("thread 1 end");
        }).start();

        new Thread(() -> {
            System.out.println("thread 2 start");
            for (long i = 0; i < 100000L; i++) {
                test.test2();
            }
            System.out.println("thread 2 end");
        }).start();
    }
}
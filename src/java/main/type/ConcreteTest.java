package java.main.type;

import java.util.Random;

/**
 * 类加载测试代码
 * <p/>
 * Created by Billin on 2017/2/24.
 */
public class ConcreteTest {

    private static class A {
        public static final int a = 1;

        public static final int b = new Random().nextInt();

        final int c = 2;

        static {
            System.out.println("class A initialization");
        }
    }

    public static void main(String[] args) {

        // Does not trigger initialization
        Class a = A.class;
        System.out.println("after create class ref");

        // Does not trigger initialization
        System.out.println(A.a);
        System.out.println("after get static filed with constance");

        // Dose trigger initialization
        System.out.println(A.b);
        System.out.println("after get static filed with nonConstance");

        /*
        RESULT:
        after create class ref
        1
        after get static filed with constance
        class A initialization
        1444548800
        after get static filed with nonConstance
         */
    }

}

/**
 * log util
 * <p/>
 * Created by billin on 16-9-20.
 */
public class Logger {

    public static void main(String[] args) {

        A a = new A();
        a.doit();

    }

    void testGetClassName() {

        // 方法1：通过SecurityManager的保护方法getClassContext()
        long start = System.currentTimeMillis();
        String clazzName = "";
        for (int i = 0; i < 600000; i++) {
            clazzName = new SecurityManager() {
                private String getClassName() {
                    return getClassContext()[3].getName();
                }
            }.getClassName();
        }
        long end = System.currentTimeMillis();

        System.out.println(clazzName + " " + (end - start));
        /*
        result:
        C 708
         */

        // 方法2：通过Throwable的方法getStackTrace()
        start = System.currentTimeMillis();
        String clazzName2 = "";
        for (int i = 0; i < 100000; i++) {
            StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
            clazzName2 = stackTraceElement.getClassName() + "@" + stackTraceElement.getMethodName();
        }
        end = System.currentTimeMillis();

        System.out.println(clazzName2 + " " + (end - start));
        /*
        result:
        C 731
         */

        //方法4：通过Thread的方法getStackTrace()
        start = System.currentTimeMillis();
        String clazzName4 = "";
        for (int i = 0; i < 100000; i++) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
            clazzName4 = stackTraceElement.getClassName() + "@" + stackTraceElement.getMethodName();
        }
        end = System.currentTimeMillis();

        System.out.println(clazzName4 + " " + (end - start));
        /*
        result
        C 662
         */
    }
}


class A {
    void doit() {
        new B().doit();
    }
}


class B {
    void doit() {
        new C().doit();
    }
}


class C implements Runnable {
    void doit() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        new Logger().testGetClassName();
    }
}

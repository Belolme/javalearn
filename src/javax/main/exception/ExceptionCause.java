package javax.main.exception;

/**
 * 异常链
 * <p/>
 * Created by Billin on 2017/2/21.
 */
public class ExceptionCause {

    private static void test1() {
        try {
            test2();
        } catch (NullPointerException ex) {

            // first right demo
//            Exception bussinessEx = new Exception("packag exception");
//            bussinessEx.initCause(ex);
//            throw bussinessEx;

            // second right demo
            // 使用 Exception 必須要在方法上申明抛出異常
            // 否則使用 RuntimeException
            throw new RuntimeException("packag exception", ex);

            // wrong demo
//            throw (Exception) ex.fillInStackTrace().initCause(ex);
        }
    }

    private static void test2() {
        test3();
    }

    private static void test3() {
        throw new NullPointerException("str is null");
    }

    public static void main(String[] args) {
        test1();
    }
}

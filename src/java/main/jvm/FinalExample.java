package java.main.jvm;

/**
 * Created by Billin on 2017/5/24.
 */
public class FinalExample {
    boolean i;                   // 普通变量
    final int j;             // final 变量
    static FinalExample obj;

    public FinalExample() {
        i = true; // 写普通域
        j = 2; // 写 final 域
    }

    public static void writer() {
        obj = new FinalExample();
    }

    public static void reader() {
        FinalExample object = obj; // 读对象引用
        boolean a = object.i; // 读普通域
        int b = object.j; // 读 final 域
        System.out.println(a + " " + b);
    }

    public static void main(String[] args) {
        writer();
        new Thread(FinalExample::reader).start();
    }
}

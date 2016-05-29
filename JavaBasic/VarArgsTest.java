/**
 * Created by billin on 16-5-29.
 * Test class for variable args param
 */
public class VarArgsTest {

    private static void m1(String s, String... ss) {
        for (String s1 : ss) {
            System.out.println(s1);
        }
    }

    static {
        System.out.println("Test m1 method");
        m1("");
        m1("aaa");
        m1("aaa", "bbb");
    }


    static {
        System.out.println("Test 复写父类可变参数方法");
        // 向上转型
        Base base = new Sub();
        base.print("hello");

        // 不转型
        Sub sub = new Sub();
        //sub.print("hello");
    }


    public static void main(String[] args) {
//        new VarArgsTest();
    }
}

// 基类
class Base {
    void print(String... args) {
        System.out.println("Base......test");
    }
}

// 子类，覆写父类方法
class Sub extends Base {
    @Override
    void print(String[] args) {
        System.out.println("Sub......test");
    }
}
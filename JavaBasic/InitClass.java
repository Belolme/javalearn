/**
 * Created by billin on 16-5-29.
 * Test for init order
 * <p>永远是静态变量先初始化，接着是非静态变量和方法，最后是构造器</p>
 * <p>静态变量、代码快和方法只初始化一次</p>
 * <p>代码快的变量属于局部变量</p>
 */
public class InitClass {

    private static void m1() {
        System.out.println("static method 1 init");
    }

    private void m2() {
        System.out.println("method 2 init");
    }

    static {
        System.out.println("static code block init");
    }

    {
        System.out.println("code block init");
    }

    InitClass() {
        System.out.println("construct init");
    }


    public static void main(String[] args) {
        new InitClass();
        new InitClass();
    }
}

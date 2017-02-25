package type;

/**
 * 自限定泛型
 * <p/>
 * Created by Billin on 2017/2/25.
 */
public class SelfBound {

    private static class SelfBounded<T extends SelfBounded<T>> {
        T element;
    }

    private static class A extends SelfBounded<A> {

    }

    private static class B extends SelfBounded<A> {

    }

    /*
    The code of bellow is not allowed,
    Because String is not extend the bound of SelfBounded<String>

    private static class C extends SelfBounded<String> {

    }
     */

    public static void main(String[] args) {
        A a = new A();
        a.element = new A();
        System.out.println(a.getClass());

        /* result:
        class type.SelfBound$A
         */
    }
}

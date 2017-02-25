package type;

/**
 * 获取泛型的类型方法
 * <p/>
 * Created by Billin on 2017/2/25.
 */
public class GetGenericType<T> {

    private T t;

    private static class A {
    }

    private static class B extends A {
    }

    public static void main(String[] args) {
        GetGenericType<A> getGenericType = new GetGenericType<>();
        getGenericType.t = new A();

        getGenericType.isInstance(new B());
        /* result:
        false
        true
        false
         */
    }

    private void isInstance(Object o) {

        // 以 o 类作为评判标准，判断 t 对象是不是o 类
        System.out.println(o.getClass().isInstance(t));

        // 以 t 类作为评判标准，判断 o 对象是不是 t 类
        System.out.println(t.getClass().isInstance(o));

        System.out.println(o.getClass().isInstance(o.getClass()));
    }
}

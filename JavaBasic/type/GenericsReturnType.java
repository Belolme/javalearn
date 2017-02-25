package type;

/**
 * 协变参数类型测试代码
 * <p/>
 * Created by Billin on 2017/2/25.
 */
public class GenericsReturnType {

    interface GenericGetter<T extends GenericGetter<T>> {
        T get();
    }

    interface Getter extends GenericGetter<Getter> {

    }

    public static void main(String[] args) {
        Getter getter = new Getter() {
            @Override
            public Getter get() {
                return this;
            }
        };

        System.out.println(getter.get().getClass());
        /* result:
        class type.GenericsReturnType$1
         */
    }
}

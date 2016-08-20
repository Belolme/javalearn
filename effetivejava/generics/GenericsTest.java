package effetivejava.generics;


import java.util.Iterator;
import java.util.List;

/**
 * Created by billin on 16-8-12.
 * Generics test
 */
public class GenericsTest {

    /**
     * 通过通配符实现受检查的弱类型
     * @param list a list
     * @return the max node of list
     */
    public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        Iterator<? extends T> i = list.iterator();
        T result = i.next();
        while (i.hasNext()) {
            T t = i.next();
            if (t.compareTo(result) > 0)
                result = t;
        }
        return result;
    }

    public static void main(String[] args) {

    }
}

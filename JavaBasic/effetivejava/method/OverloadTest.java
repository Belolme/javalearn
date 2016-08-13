package effetivejava.method;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by billin on 16-8-13.
 * OverLoad Test
 */
public class OverloadTest {

    public static void print(Object... args) {
        for (Object o : args)
            System.out.println(o.toString());

        System.out.println("in print(Object... args)");
    }

    /*
    Conn't compiled
     */
//    public static void print(Object[] objects){
//    }

    public static void print(Object o) {
        System.out.println(o.toString());

        System.out.println("in print(Object o)");
    }

    public static void print(Integer i) {
        System.out.println(i);

        System.out.println("int print(Integer i)");
    }

    public static void print(String s) {
        System.out.println(s);

        System.out.println("in print(String s)");
    }

    public static void main(String[] args) {

        print("aaa");
        /*
        output:
        aaa
        in print(String s)
         */

        print("first", "second");
        /*
        output:
        first
        second
        in print(Object... args)
         */

        print(5);
        /*
        output:
        5
        in print(Integer i)
         */


        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();
        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }
        for (int i = 0; i < 3; i++) {
            set.remove(i);
            list.remove(i); //list.remove((Integer) i);
        }
        System.out.println("set: " + set);
        System.out.println("list: " + list);
        /*
        set: [-3, -2, -1]
        list: [-2, 0, 2]
         */
    }
}

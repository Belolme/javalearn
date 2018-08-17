package javax.main.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * 全排列算法
 * <p/>
 * Created by billin on 16-10-10.
 */
public class MyPermutation {


    private static void permu(List<Integer> list, int start) {
        if (start == list.size()) {
            System.out.println(list);
            return;
        }

        for (int i = start; i < list.size(); i++) {
            int startNumber = list.get(start);
            list.set(start, list.get(i));
            list.set(i, startNumber);
            permu(list, start + 1);
            list.set(i, list.get(start));
            list.set(start, startNumber);
        }
    }

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
        list.add(8);
        list.add(9);

        permu(list, 0);
    }

}

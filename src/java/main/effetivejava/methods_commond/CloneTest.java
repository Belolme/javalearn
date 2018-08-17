package java.main.effetivejava.methods_commond;

import java.util.Arrays;

/**
 * Created by billin on 16-8-12.
 * Clone test
 */
public class CloneTest implements Cloneable {


    int num = 0;
    CloneArr[] arr;

    public CloneTest() {
        arr = new CloneArr[]{new CloneArr("string1"), new CloneArr("string2"), new CloneArr("string3")};
    }

    @Override
    public String toString() {
        return num + " " + Arrays.toString(arr);
    }

    @Override
    protected CloneTest clone() throws CloneNotSupportedException {
        CloneArr[] arrClone = arr.clone();
        CloneTest clone = (CloneTest) super.clone();
        clone.arr = arrClone;
        return clone;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest ori = new CloneTest();
        CloneTest clone = (CloneTest) ori.clone();

        print(ori, clone);
        /*
          return
          0 [string1, string2, string3]
          0 [string1, string2, string3]
         */

        ori.arr[0] = new CloneArr("hello");
        print(ori, clone);
        /*
        return
        0 [hello, string2, string3]
        0 [string1, string2, string3]
         */

        ori.arr[1].s = "hi";
        print(ori, clone);
        /*
        return
        0 [hello, hi, string3]
        0 [string1, hi, string3]
         */

    }

    public static void print(CloneTest... objs) {
        for (CloneTest obj : objs)
            System.out.println(obj);
    }
}

class CloneArr implements Cloneable {
    String s;

    public CloneArr(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}

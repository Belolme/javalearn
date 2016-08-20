/**
 * Created by billin on 16-5-28.
 * Syntax demo for java
 */
public class JavaShiftOperator {


    private static void shiftOperator() {
        System.out.printf("%08x%n", 0xffffffff >> 4);
        /*
        output:
        ffffffff
         */

        System.out.printf("%08x%n", 0xffffffff >>> 4);
        /*
        output
        0fffffff
         */
    }

    public static void main(String[] args) {
        shiftOperator();
    }
}

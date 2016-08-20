package effetivejava.general_programming;

import java.util.Random;

/**
 * Created by billin on 16-8-13.
 * A test for Random class method
 */
public class RandomGetter {

    private static final Random mRandom = new Random();

    private static int random(int n) {
        // deeply flawed
        return Math.abs(mRandom.nextInt()) % n;
    }

    private static int randomRight(int n) {
        return mRandom.nextInt(n);
    }

    public static void main(String[] args) {

        int n = 2 * (Integer.MAX_VALUE / 3);
        int low1 = 0;
        int low2 = 0;
        for (int i = 0; i < 1000000; i++) {
            if (random(n) < n / 2)
                low1++;

            if (randomRight(n) < n / 2)
                low2++;
        }

        System.out.println(low1);
        /*
        output:
        666978
         */

        System.out.println(low2);
        /*
        output:
        500358
         */

    }

}

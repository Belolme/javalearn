/**
 * Created by billin on 16-5-29.
 * Java Flow Control test
 */
public class FlowControl {

    private static void breakTag() {

        breakTag:
        for (int i = 0; i < 10; i++) {

            for (int j = 0; j < 10; j++) {
                System.out.printf("i=%d, j=%d\n", i, j);
                if (i == 5)
                    break breakTag;

            }
        }

        System.out.println("For loop ended");
    }

    private static void continueTag() {

        continueTag:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.printf("i=%d, j=%d\n", i, j);
                if (i == 5)
                    continue continueTag;
            }
        }
        System.out.println("For loop ended");
    }

    public static void main(String[] args) {
        breakTag();
        continueTag();
    }

}

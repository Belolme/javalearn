import java.util.stream.IntStream;

public class NumberClassifier {

    public static IntStream factorOf(final int number) {
        return IntStream.range(1, number).filter((int x) -> number % x == 0);
    }

    public static Boolean isPerfect(int number) {
        return factorOf(number).sum() == number;
    }

    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        IntStream.range(1, 10000).parallel().filter(NumberClassifier::isPerfect).forEach(System.out::println);
        final long endTime = System.currentTimeMillis();
        System.out.println("Stop. The millis time is :" + (endTime - startTime));
    }
}
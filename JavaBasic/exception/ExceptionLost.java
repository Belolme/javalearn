package exception;

/**
 * 異常丟失測試類
 * <p/>
 * Created by Billin on 2017/2/21.
 */
public class ExceptionLost {

    static class ImportanceException extends Exception {
        @Override
        public String toString() {
            return "importance exception";
        }
    }

    static class DisposeException extends Exception {
        @Override
        public String toString() {
            return "dispose exception";
        }
    }

    static class Test {
        void doIt() throws ImportanceException {
            throw new ImportanceException();
        }

        void dispose() throws DisposeException {
            throw new DisposeException();
        }
    }

    public static void main(String[] args) {

        // The first situation with exception lose
        try {
            Test test = new Test();

            try {
                System.out.println("1");
                test.doIt();
            } finally {
                System.out.println("2");
                test.dispose();
            }
        } catch (ImportanceException | DisposeException e) {
            System.out.println("3");
            e.printStackTrace();
        }


        // The second situation with exception lose
        try {
            throw new RuntimeException();
        } finally {

            // Using "return" inside the finally block
            // will silence any thrown exception.
            return;
        }
    }

}

import fj.Unit;
import fj.data.Array;

/**
 * Created by billin on 16-6-11.
 * An demo for runnable
 */
public class RunnableDemo {

//    static {
//        new Thread(new PrintNumTask()).start();
//    }

    static {
        System.out.println("Print Number with new Thread");
        new PrintNumTask2();
    }


    public static void main(String[] args) {
    }
}

class PrintNumTask implements Runnable{

    @Override
    public void run() {
        Array.range(0,100).foreach(i -> {
            System.out.print(i + " in ");
            System.out.println(Thread.currentThread().getName());
            return Unit.unit();
        });
    }
}

class PrintNumTask2 implements Runnable{

    public PrintNumTask2(){
        new Thread(this).start();
    }

    @Override
    public void run() {
        new PrintNumTask().run();
    }
}
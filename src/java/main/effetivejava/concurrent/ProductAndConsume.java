package java.main.effetivejava.concurrent;

/**
 * product and consume model
 * Created by Billin on 2017/2/28.
 */
public class ProductAndConsume {

    /**
     * if product is greater than this value,
     * notify product the warehouse is full.
     */
    private static final int MAX_PRODUCT = 100;

    /**
     * if product is less than this value,
     * notify consumer out of store.
     */
    private static final int MIN_PRODUCT = 1;

    private volatile int product = 0;

    /**
     * 生产者生产出来的产品交给店员
     */
    private synchronized void produce() {
        if (this.product >= MAX_PRODUCT) {
            try {
                System.out.println("产品已满,请稍候再生产");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        try {
            Thread.sleep(100);
            this.product++;
            System.out.println("生产者生产第" + this.product + "个产品.");
            notifyAll();   //通知等待区的消费者可以取出产品了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费者从店员取产品
     */
    private synchronized void consume() {
        if (this.product <= MIN_PRODUCT) {
            try {
                System.out.println("缺货,稍候再取");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }

        System.out.println("消费者取走了第" + this.product + "个产品.");
        this.product--;
        notifyAll();   //通知等待去的生产者可以生产产品了
    }

    public static void main(String[] args) {
        ProductAndConsume test = new ProductAndConsume();

        new Thread(() -> {
            while (true) {
                test.consume();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                test.produce();
            }
        }).start();
    }
}

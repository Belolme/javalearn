package java.main.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by billin on 16-5-26.
 * A test demo for Schedules
 */
public class SchedulesTest {

    private void doWorkWithSchedules(final int i, final int j) {

        Scheduler.Worker worker = Schedulers.io().createWorker();
        worker.schedule(() -> {
            if (i < j) {
                System.out.println(i);
                doWorkWithSchedules(i + 1, j);
            }

            worker.unsubscribe();

        }, 1, TimeUnit.NANOSECONDS);

    }

    public static void main(String[] args) {

        SchedulesTest schedulesTest = new SchedulesTest();

//        Action0 action = () -> {
//            Array<Integer> list = Array.range(1, 100);
//            for (int i : list) {
//                System.out.println(i);
//            }
//        };

        schedulesTest.doWorkWithSchedules(1, 10000000);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

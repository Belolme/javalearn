package java.main.rx;

import fj.F;
import fj.function.Integers;
import fj.function.Longs;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by billin on 16-5-28.
 * Rxjava test for repeat
 */
public class Repeat {

    private static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    private static void printDate() {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .filter(i -> i % 2 == 0)
                .map(i -> getDate())
                .observeOn(Schedulers.io())
                .toBlocking()
                .subscribe(System.out::println);
    }

    public static void main(String[] args) {
        printDate();
    }

}

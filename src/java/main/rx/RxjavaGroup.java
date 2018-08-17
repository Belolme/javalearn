package java.main.rx;

import rx.Observable;
import rx.observables.GroupedObservable;

import java.util.concurrent.TimeUnit;

/**
 * Created by billin on 16-5-28.
 * Test for rxjava group
 */
public class RxjavaGroup {

    private static void groupLong(){
        Observable<GroupedObservable<Boolean, Long>> groupObservaable = Observable.interval(1, TimeUnit.SECONDS)
                .take(20)
                .groupBy(i -> i%2==0);

        Observable.concat(groupObservaable)
                .toBlocking()
                .subscribe(System.out::println);

    }

    public static void main(String[] args) {
        groupLong();
    }
}

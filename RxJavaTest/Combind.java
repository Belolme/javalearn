import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * Created by billin on 16-5-28.
 * Test for zip
 */
public class Combind {

    private static Observable<Long> tictac = Observable.interval(1, TimeUnit.SECONDS);

    private static void merge(){
        Observable.range(1, 100)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(i->Long.valueOf(i.toString()))
                .mergeWith(tictac)
//                .toBlocking()
                .subscribe(System.out::println);
    }

    private static void zip(){

        Observable.range(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .zipWith(tictac, (i, j) -> String.format("%d: %d", i, j))
                .toBlocking()
                .subscribe(System.out::println);

    }

    private static void join(){

        Observable.range(1, 10)
                .join(tictac,
                        integer -> Observable.timer(4, TimeUnit.SECONDS),
                        aLong -> Observable.timer(2, TimeUnit.SECONDS),
                        (i, j) -> String.format("%d: %d, ", i, j))
//                .toBlocking()
                .subscribe(System.out::print);
    }

    private static void start(){
        tictac.startWith(100L)
                .subscribe(System.out::println);
    }

    private static void concat(){
        tictac.take(2).concatWith(Observable.just(100L)).subscribe(System.out::println);
    }

    private static void combineLatest(){

        Observable<Integer> integerObservable = Observable.range(1, 10);
        Observable.combineLatest(integerObservable, tictac.take(10), (i, j) -> i*j).toBlocking().subscribe(System.out::println);
    }

    private static void withLatestFrom(){
        Observable.range(1, 10).withLatestFrom(tictac.take(10), (i, j) -> i*j).toBlocking().subscribe(System.out::println);
    }



    public static void main(String[] args) {
//        merge();
//        join();
//        start();
//        concat();
//        combineLatest();
//        withLatestFrom();
//        zip();
    }
}

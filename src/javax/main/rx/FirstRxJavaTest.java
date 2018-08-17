package javax.main.rx;

import fj.Unit;
import fj.data.Array;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.Arrays;

/**
 * Created by billin on 16-5-25.
 * Test RxJava class
 */
public class FirstRxJavaTest{

    public static void main(String[] args) {

        Array<Integer> array = Array.range(1, 10000000);

        long startTime = System.currentTimeMillis();
        Observable.from(array)
                .map(i -> String.format("%d ", i))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .toBlocking()
                .subscribe(System.out::println);
        long endTime = System.currentTimeMillis();


        long startTime1 = System.currentTimeMillis();
        array.map(i -> String.format("%d ", i)).foreach(s -> {
            System.out.println(s);
            return Unit.unit();
        });
        long endTime1 = System.currentTimeMillis();


        long startTime2 = System.currentTimeMillis();
        java.util.List<Integer> javaArray = Arrays.asList(array.toJavaArray());
        javaArray.stream().parallel().map(i -> String.format("%d ", i)).peek(System.out::println).count();
        long endTime2 = System.currentTimeMillis();

        //经过比较， 使用rxjava的时间为28.77秒， fj的时间为38.60秒, ArrayList的并行处理为38.00秒
        System.out.println("Time = " + (endTime - startTime));
        System.out.println("Time = " + (endTime1 - startTime1));
        System.out.println("Time = " + (endTime2 - startTime2));
    }
}

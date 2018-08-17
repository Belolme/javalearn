package java.main.rx;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import rx.schedulers.Schedulers;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by billin on 16-5-26.
 * Use RxJava and OKHttp for network requires
 */
public class RxJavaNetWorkRequireTest {

    private static String networkRequire(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        String page = "";

        try {
            Response response = client.newCall(request).execute();
            page = response == null ? "" : response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return page;
    }

    private static void writeToDish(String s) {
        File path = new File("/home/billin/Documents/tmp/page.html");

        try {
            new PrintWriter(path, "GBK").print(s);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static List<String> parseHref(String string) {

        return Collections.singletonList(string).stream().parallel()
                .map(s -> s.replace("&nbsp;", " "))
                .map(Jsoup::parse)
                .flatMap(document -> document.getElementsByTag("a").stream())
                .filter(ele -> !ele.hasAttr("title"))
                .filter(ele -> !ele.hasAttr("class"))
                .filter(ele -> !ele.hasAttr("target"))
                .map(Element::toString)
                .collect(Collectors.toList());
    }

    private static void printURL(String url) {

        rx.Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(RxJavaNetWorkRequireTest::networkRequire)
                .observeOn(Schedulers.computation())
                .flatMap(s -> rx.Observable.from(parseHref(s)))
                .observeOn(Schedulers.io())
                .toBlocking()                                       //阻塞主线程关闭
                .subscribe(s -> System.out.println(url));
    }

    public static void main(String[] args) {

        String netEast = "http://www.163.com";
        String sina = "http://www.sina.com.cn/";
        String baidu = "http://www.baidu.com";
        String tencent = "http://www.qq.com/";
        String taobao = "https://www.taobao.com/";
        String jd = "https://www.jd.com/";
        String tmall = "https://www.tmall.com/";
        String mi = "http://www.mi.com/";

        long startTime = System.currentTimeMillis();
        printURL(sina);
        printURL(netEast);
        printURL(baidu);
        printURL(tencent);
        printURL(taobao);
        printURL(jd);
        printURL(tmall);
        printURL(mi);
        long endTime1 = System.currentTimeMillis();

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        long endTime = System.currentTimeMillis();

        System.out.println("Time = " + (endTime1 - startTime));
        System.out.println("Time = " + (endTime - startTime));
    }
}
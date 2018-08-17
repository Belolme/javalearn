package javax.main.io.net.spider.student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fj.P2;
import fj.Unit;
import fj.data.Array;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fj.F;
import fj.data.List;
import fj.data.Option;

public class ParseCourse {

    private static String readFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        }
    }

    /*
     * 一个很奇怪的问题就是在一份代码中,越往后执行的代码越快,前面代码工作量越大,后面的代码运行越快
     */
    public static void main(String[] args) throws IOException {
        GetHtml getHtml = new GetHtml("3114002487", "19950515");
//        File file = new File("/home/billin/Downloads/a.html");

//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        String content = readFile("/home/billin/Downloads/a.html");
//        content = "123";
        content = getHtml.getLessonHtml();

//        ParseHtml parseHtml = new ParseHtml();
//        long startTime = System.currentTimeMillis();
//        java.util.List<Map<String, String>> courseList = parseHtml.parseLessonHtml(content);
//        courseList.stream().peek(i -> System.out.println(i.toString())).count();
//        long endTime = System.currentTimeMillis();
//        System.out.println(endTime - startTime);

        long startTime1 = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++)
//        parse(content);
        StudentInformationParser studentInformationParser
                = new StudentInformationParser();

//        studentInformationParser.parse(content);
        studentInformationParser.parseCourseTable(content).stream().peek(i->System.out.println(i.toString())).count();
        long endTime1 = System.currentTimeMillis();
        System.out.println(endTime1 - startTime1);

    }


    private static void parse(String html) {
        if (html == null) return;

        html = html.replaceAll("&nbsp;", " ");            //replace all "&nbsp;" to space

        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("td");
        List<Element> elementList = List.fromIterator(elements.iterator());
//        Show.listShow(Show.<Element>showS(Element::text)).println(elementList);

        listInformation(elementList);
    }

    private static List<String> unfoldCourse(String s) {
        String[] strings = s.split("[\\s]");
        if (strings.length < 7)
            return List.arrayList(s);

        P2<List<String>, List<String>> splitList = List.arrayList(strings).splitAt(4);
        return unfoldCourse(splitList._2().foldLeft1(s1 -> s2 -> s1 + " " + s2))
                .conss(splitList._1().foldLeft1(s1 -> s2 -> s1 + " " + s2) + " " + strings[strings.length - 2] + " " + strings[strings.length - 1]);
    }

    private static int courseTime = 1;

    private static void listInformation(List<Element> list) {
        if (list.isEmpty()) return;

        F<String, F<Integer, F<String, Option<String>>>> regexFunction = string -> group -> regex -> {
            Matcher matcher = Pattern.compile(regex).matcher(string);
            return matcher.find() ? Option.fromString(matcher.group(group)) : Option.none();
        };
        F<String, Option<String>> regexForString = regexFunction.f(list.head().text()).f(1);
        Consumer<Option<String>> c = opt -> opt.forEach(System.out::println);
//
        regexFunction.f(list.head().text()).f(1).f("学号\\D+(\\d+)").forEach(System.out::println);
        c.accept(regexForString.f("姓名：+(.+)"));
        c.accept(regexForString.f(".+班级\\D+(\\d+)"));
        c.accept(regexForString.f("性别：(.+)"));
        c.accept(regexForString.f(".+院系：(\\D+)"));
        c.accept(regexForString.f(".+专业：(.+)"));

        Consumer<List<Element>> courseParse = l -> {
            F<String, F<Integer, Integer>> getInteger = s -> groupId -> Integer.parseInt(regexFunction.f(s).f(groupId).f("(\\d+)-(\\d+)").some());

            List<String> courseList = l.tail().take(5).map(Element::text);
            List.range(0, 5)
                    .map(i -> courseList.index(i) + " " + i + " " + courseTime)
                    .filter(s -> s.contains("第"))
                    .bind(ParseCourse::unfoldCourse)
//            courseStringList.foreach(courseSLE -> {
//                System.out.println(courseSLE + "hi");
//                return Unit.unit();
//            });
                    .map(ele -> Array.array(ele.split("[\\s]")).filter(s -> !s.equals("")))
                    .bind(mArray -> {
                        String string = mArray.get(1);
                        Matcher matcher = Pattern.compile("第(.+)周").matcher(string);
                        String weekString = matcher.find() ? matcher.group(1) : "";

                        return List.arrayList(weekString.split("[\\s,]"))
                                .filter(str -> !str.equals(""))
                                .bind(str -> str.contains("-") ? List.range(getInteger.f(str).f(1), getInteger.f(str).f(2) + 1) : List.arrayList(Integer.parseInt(str)))
                                .map(i -> {
//                                    HashMap<Integer, String> hashMap = HashMap.fromMap(map);
//                                    hashMap.set(5, String.valueOf(i));
//                                    java.util.HashMap<Integer, String> mapClone = (java.util.HashMap<Integer, String>) mArray.clone();
//                                    mapClone.put(5, String.valueOf(i));
                                    return mArray.toList().conss(String.valueOf(i));
                                });
                    })
                    .map(mList -> {
                        int a = Integer.parseInt(mList.index(0)) << 8 | (Integer.parseInt(mList.index(5)) << 4) | Integer.parseInt(mList.index(6));
                        java.util.List<String> javaList = mList.toJavaList();
                        javaList.remove(0);
                        javaList.remove(4);
                        javaList.remove(4);
                        javaList.remove(1);

                        return List.fromIterator(javaList.<String>iterator()).conss(String.valueOf(a));
                    })
                    .foreach(mList -> {
                        System.out.println(mList.toString());
                        return Unit.unit();
                    });

            courseTime = courseTime + 1;
        };

        Matcher mat = Pattern.compile("第.+节").matcher(list.head().text());
        if (mat.find())
            courseParse.accept(list);

        listInformation(list.tail());
    }

}

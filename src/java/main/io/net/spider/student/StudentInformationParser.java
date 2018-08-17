package java.main.io.net.spider.student; /**
 * Created by billin on 16-5-13.
 */

import fj.F;
import fj.P2;
import fj.data.Array;
import fj.data.HashMap;
import fj.data.List;
import fj.data.Option;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fj.data.Option.none;

/**
 * Created by billin on 16-5-13.
 * A parser for parse student information, include course table, student basis information
 */
public class StudentInformationParser {

    private static final int STUDENT_INFORMATION = 0;
    private static final int STUDENT_COURSE = 1;
    private HashMap<String, List<Element>> mElementsCache = HashMap.hashMap();
    private int mCourseTime = 0;

    /*
     * Get student basis information
     * @return - [studentId, name, sex, classNumber, major]
     */
    public java.util.List parse(String beParse) {
        return parse(beParse, STUDENT_INFORMATION).toJavaList();
    }

    public java.util.List parseCourseTable(String beParse) {
        mCourseTime = 0;
        return parse(beParse, STUDENT_COURSE).toJavaList();
    }


    private List parse(String beParse, int parseParam) {
        List<Element> elementList;
        if (mElementsCache.get(beParse).isSome()) {
            elementList = mElementsCache.get(beParse).some();
        } else {
            beParse = beParse.replace("&nbsp;", " ");
            Document doc = Jsoup.parse(beParse);
            elementList = fj.data.List.fromIterator(doc.getElementsByTag("td").iterator());
            mElementsCache.set(beParse, elementList);
        }

        return listInformation(elementList, parseParam);
    }

    private List listInformation(List<Element> elementList, int parseParam) {

        if (elementList.isEmpty())
            return List.nil();


        if (parseParam == STUDENT_INFORMATION) {
            F<String, Option<String>> catchInformation = s -> {
                F<String, Option<String>> regexForString = regexFunction.f(s).f(1);
                if (regexForString.f("姓名：+(.+)").isSome()) return regexForString.f("姓名：+(.+)");
                if (regexForString.f(".+班级\\D+(\\d+)").isSome())
                    return regexForString.f(".+班级\\D+(\\d+)");
                if (regexForString.f("性别：(.+)").isSome()) return regexForString.f("性别：(.+)");
                if (regexForString.f(".+院系：(\\D+)").isSome())
                    return regexForString.f(".+院系：(\\D+)");
                if (regexForString.f(".+专业：(.+)").isSome()) return regexForString.f(".+专业：(.+)");

                return Option.none();
            };

            if (catchInformation.f(elementList.head().text()).isSome())
                return listInformation(elementList.tail(), parseParam).conss(catchInformation.f(elementList.head().text()).some());
            else
                return listInformation(elementList.tail(), parseParam);
        } else if(parseParam == STUDENT_COURSE){
            Matcher mat = Pattern.compile("第.+节").matcher(elementList.head().text());

            if(mat.find())
                return listInformation(elementList.tail(), parseParam).append(mCourseParse.f(elementList));
            else
                return listInformation(elementList.tail(), parseParam);
        }

        return List.nil();
    }

    /*
     * Use to unfold a multi-course line to multi-lines
     */
    private static List<String> unfoldCourse(String s) {
        String[] strings = s.split("[\\s]");
        if (strings.length < 7)
            return List.arrayList(s);

        P2<List<String>, List<String>> splitList = List.arrayList(strings).splitAt(4);
        return unfoldCourse(splitList._2().foldLeft1(s1 -> s2 -> s1 + " " + s2))
                .conss(splitList._1().foldLeft1(s1 -> s2 -> s1 + " " + s2) + " " + strings[strings.length - 2] + " " + strings[strings.length - 1]);
    }

    private F<String, F<Integer, F<String, Option<String>>>> regexFunction = string -> group -> regex -> {
        Matcher matcher = Pattern.compile(regex).matcher(string);
        return matcher.find() ? Option.fromString(matcher.group(group)) : none();
    };


    private F<List<Element>, List<java.util.List<String>>> mCourseParse = l -> {
        F<String, F<Integer, Integer>> getInteger = s -> groupId -> Integer.parseInt(regexFunction.f(s).f(groupId).f("(\\d+)-(\\d+)").some());
        List<String> courseList = l.tail().take(5).map(Element::text);
        mCourseTime = mCourseTime + 1;

        return List.range(0, 5)
                .map(i -> courseList.index(i) + " " + i + " " + mCourseTime)
                .filter(s -> s.contains("第"))
                .bind(StudentInformationParser::unfoldCourse)
                .map(ele -> Array.array(ele.split("[\\s]")).filter(s -> !s.equals("")))
                .bind(mArray -> {
                    String string = mArray.get(1);
                    Matcher matcher = Pattern.compile("第(.+)周").matcher(string);
                    String weekString = matcher.find() ? matcher.group(1) : "";

                    return List.arrayList(weekString.split("[\\s,]"))
                            .filter(str -> !str.equals(""))
                            .bind(str -> str.contains("-") ? List.range(getInteger.f(str).f(1), getInteger.f(str).f(2) + 1) : List.arrayList(Integer.parseInt(str)))
                            .map(i -> mArray.toList().conss(String.valueOf(i)));
                })
                .map(mList -> {
                    int a = Integer.parseInt(mList.index(0)) << 8 | (Integer.parseInt(mList.index(5)) << 4) | Integer.parseInt(mList.index(6));
                    java.util.List<String> javaList = mList.toJavaList();
                    javaList.remove(0);
                    javaList.remove(4);
                    javaList.remove(4);
                    javaList.remove(1);

                    return List.fromIterator(javaList.<String>iterator()).conss(String.valueOf(a)).toJavaList();
                });
    };
}

package java.main.io.net.spider.student;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Billin Deng on 2015/10/14.
 * 解析网页类
 */
public class ParseHtml {

    /*
     * 解析课程表
     * @param String html - html of syllabus
     * return container;if html is null return null
     */
    public List<Map<String, String>> parseLessonHtml(String html) {

        /*
         * Parse html file to Document by Jsoup
         */
        if (html == null) return new ArrayList<>();

        html = html.replaceAll("&nbsp;", " ");            //replace all "&nbsp;" to space
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("td");

        final int DAY_OF_WEEK = 7;
        final int LESSONS_OF_DAY = 5;
//        for(Element e:elements)
//        	System.out.println(e.text());

        int i = 0;
        int elementslength = elements.size();

        /*
         * if html is null and return null
         */
        if (elementslength == 0) {
            return new ArrayList<>();
        }

        List<Map<String, String>> lessonInformation = new ArrayList<>();
//        HashMap<String,String> studentInformation = new HashMap<String,String>();


        /*
         * put student information to a hashmap(studentInformation)
         */
//        for(i=0;i<6;i++) {
//            //get element from elements
//            org.jsoup.nodes.Element element = elements.get(i);
//            String elementText = element.text();
//            //instant pattern match text and put in map
//            String patternString = "：";
//            Pattern pattern = Pattern.compile(patternString);
//            String[] keysOrValues = pattern.split(elementText);
//            if(keysOrValues.length == 2){
//                studentInformation.put(keysOrValues[0],keysOrValues[1]);
//            } else if(keysOrValues.length == 1) {
//                studentInformation.put(keysOrValues[0],"null");
//            }
//        }

        /*
         * put course detail to a container(lessonInformation)
         */
        int time = 1;
        String patternString = "第.+节|晚.+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher;

        //retrieve "第一大节"
        do {
            //get element from elements
            org.jsoup.nodes.Element element = elements.get(i++);
            matcher = pattern.matcher(element.text());
        } while (!matcher.matches());

        for (int j = 0; j < (LESSONS_OF_DAY * DAY_OF_WEEK); ) {

            //get element from elements
            org.jsoup.nodes.Element element = elements.get(i);
            matcher = pattern.matcher(element.text());
            i++;

            //judge matcher is match or not; If not, parse course information to container
            if (!matcher.matches()) {
                Pattern coursePattern = Pattern.compile("\\s");
                String[] courseDetailInformation = coursePattern.split(element.text());

                if (courseDetailInformation.length > 1) {
                    for (int k = 0; k < courseDetailInformation.length; ) {
//                        coursePattern = Pattern.compile("\\D+");
                        Pattern weekOfSessionPattern = Pattern.compile("[^-\\d]");
                        int courseWeekBegin;
                        int courseWeekEnd;
                        String[] courseWeekOfSession;
                        String courseWeekOfSessionString;
                        int dayOfWeek;
                        int index;             //This is an index for course Week of session
                        String course;
                        String teacher;
                        String site;


                        //parse course name
                        course = courseDetailInformation[k];

                        //get course's week of session
                        courseWeekOfSessionString = courseDetailInformation[++k];

                        //parse site and teacher name
                        site = courseDetailInformation[++k];
                        teacher = courseDetailInformation[++k];

                        //calculate day of week
                        dayOfWeek = (j % 7) + 2;
                        if (dayOfWeek == 8)
                            dayOfWeek = 1;

//            			System.out.print(courseDetailInformation[0]);
//            			System.out.println(" j=" + j+" dayofw=" + dayOfWeek);

                        //parse course's week of session and put the information to container
                        courseWeekOfSession = weekOfSessionPattern.split(courseWeekOfSessionString);
                        weekOfSessionPattern = Pattern.compile("-");
                        for (int l = 1; l < courseWeekOfSession.length; l++) {
//                            System.out.println(courseWeekOfSession[l]);
                            Matcher m = weekOfSessionPattern.matcher(courseWeekOfSession[l]);
                            if (m.find()) {
                                String s[] = weekOfSessionPattern.split(courseWeekOfSession[l]);
                                courseWeekBegin = Integer.parseInt(s[0]);
                                courseWeekEnd = Integer.parseInt(s[1]);
                                for (index = courseWeekBegin; index <= courseWeekEnd; index++) {
                                    Map<String, String> map = new LinkedHashMap<>();
                                    map.put("course", course);
                                    map.put("teacher", teacher);
                                    map.put("site", site);
                                    map.put("week_of_session", index + "");
                                    map.put("day_of_week", dayOfWeek + "");
                                    map.put("time", time + "");
                                    lessonInformation.add(map);
                                }
                            } else {
                                Map<String, String> map = new LinkedHashMap<>();
                                index = Integer.parseInt(courseWeekOfSession[l]);
                                map.put("course", course);
                                map.put("teacher", teacher);
                                map.put("site", site);
                                map.put("week_of_session", index + "");
                                map.put("day_of_week", dayOfWeek + "");
                                map.put("time", time + "");
                                lessonInformation.add(map);
                            }
                        }
                        k++;
                    }
                }
                j++;
            } else {
                time++;
            }
        }

        return lessonInformation;
    }

    /*
     * 解析课程基本信息，包括开课号、学分、教师、课程类型（上课班级）
     */
    /*
    public List<CourseBasisData> parseLessonBasisData(String courseTableHtml){

        if(courseTableHtml==null)
            return null;

        courseTableHtml = courseTableHtml.replaceAll("&nbsp;", " ");

        Document doc = Jsoup.parse(courseTableHtml);

        Elements elements = doc.getElementsByTag("td");

        List<CourseBasisData> list = new ArrayList<>();
        CourseBasisData courseBasisData = new CourseBasisData();

        boolean status = false;
        int counter = 1;

        for(org.jsoup.nodes.Element e : elements){

            if(Pattern.matches("上课班级", e.text())){
                status = true;
            } else if(status && !Pattern.matches("查询日期.", e.text())){
                switch (counter){
                    case 1:
                        courseBasisData.setCourseId(e.text());
                        counter++;
                        break;

                    case 2:
                        courseBasisData.setCourseName(e.text());
                        counter++;
                        break;

                    case 3:
                        courseBasisData.setCourseWeight(e.text());
                        counter++;
                        break;

                    case 4:
                        courseBasisData.setTeacher(e.text());
                        counter++;
                        break;

                    case 5:
                        counter++;
                        break;

                    case 6:
                        courseBasisData.setCourseType(e.text());
                        list.add(courseBasisData);
                        courseBasisData = new CourseBasisData();
                        counter = 1;
                        break;

                    default:
                        break;
                }
            }

        }


        return list;


    }
*/


    /*
     * 解析学生基本信息
     * @param String html - 学生基本信息网页页面
     * return Map
     */
    /*
    public Map<String, String> parseStudentsBasisInformation(String html) {

        Map<String, String> studentInformationMap = new HashMap<>();

        html.replaceAll("&nbsp;", " ");
        Document doc = Jsoup.parse(html);

        Elements elements = doc.getElementsByTag("td");

        String name = null;
        String value;
        int counter = 0;
        for(org.jsoup.nodes.Element e : elements){

            if(!e.hasText()||e.text().equals("学生学籍"))
                continue;

            if(counter==0) {
                if(e.text().equals(" ")){
                    name = null;
                }else name = e.text();

                counter++;
            }else{
                value = e.text();
                studentInformationMap.put(name, value);
                counter = 0;
            }

        }

        return studentInformationMap;

    }

*/

    /*
     * Parse teacher's course
     * @param String html
     * return List<Map<String, String>>
     */
/*    public static List<Map<String, String>> parseTeacherLesson(String html){

        html = html.replaceAll("&nbsp;", " ");			//replace all "&nbsp;" to space
        Document doc = Jsoup.parse(html);

        List<Map<String, String>> result = new ArrayList<>();

        Elements elements = doc.getElementsByTag("span");
        int size = elements.size();
        int i = 0;					//The index of elements

        while(i<size) {

            if(i == 0 || i == size-1){
                i++;
            }else {

                boolean status = false;

                String course = elements.get(i+1).text();
                String teacher = elements.get(i+8).text();
                String site = elements.get(i+7).text();

                String time = elements.get(i+6).text();
                String []splitedTime;

                List<String> weekOfSessionList = new ArrayList<>();
                String dayOfWeekString = null;
                String timeNOString = null;

                int [][]weekOfSession;
                int dayOfWeek;
                int timeNO;

                //Construct Pattern and Matcher
                Pattern pattern = Pattern.compile("第([^,]+)周");
                Matcher matcher;

                //Set weekOfSessionList
                matcher = pattern.matcher(time);
                while(matcher.find()){
                    status = true;
                    weekOfSessionList.add(matcher.group(1));
                }
                //If the status if false and go to last
                if(status){
                    weekOfSession = new int [weekOfSessionList.size()][2];
                    //Initialization the array
                    for(int n=0; n<weekOfSession.length; n++){
                        for(int m=0; m<2; m++){
                            weekOfSession[n][m] = 0;
                        }
                    }
                    int weekOfSessionIndex = 0;
                    for(String s : weekOfSessionList){
                        pattern = Pattern.compile("-");
                        splitedTime = pattern.split(s);
                        int s1Index = 0;
                        for(String s1 : splitedTime) {
                            weekOfSession[weekOfSessionIndex][s1Index] = Integer.parseInt(s1);
                            s1Index++;
                        }
                        weekOfSessionIndex++;
                    }

                    //Set dayOfWeekString
                    pattern = Pattern.compile("星期.");
                    matcher = pattern.matcher(time);
                    while(matcher.find()){
                        dayOfWeekString = matcher.group();
                        break;
                    }
                    switch (dayOfWeekString){

                        case "星期日":
                            dayOfWeek = 1;
                            break;

                        case "星期一":
                            dayOfWeek = 2;
                            break;

                        case "星期二":
                            dayOfWeek = 3;
                            break;

                        case "星期三":
                            dayOfWeek = 4;
                            break;

                        case "星期四":
                            dayOfWeek = 5;
                            break;

                        case "星期五":
                            dayOfWeek = 6;
                            break;

                        case "星期六":
                            dayOfWeek = 7;
                            break;

                        default:
                            dayOfWeek = 1;
                            break;

                    }

                    //Set timeString
                    pattern = Pattern.compile(".第(.*)节");
                    matcher = pattern.matcher(time);
                    while(matcher.find()){
                        timeNOString = matcher.group(1);
                    }
                    if(timeNOString==null){
                        timeNO = 5;
                    } else if( timeNOString.getBytes()[0] == '1' ){
                        timeNO = 1;
                    } else if( timeNOString.getBytes()[0] == '3'){
                        timeNO = 2;
                    } else if(timeNOString.getBytes()[0] == '5'){
                        timeNO = 3;
                    } else{
                        timeNO = 4;
                    }

                    weekOfSessionIndex = 0;
                    while(weekOfSessionIndex<weekOfSession.length){

                        if(weekOfSession[weekOfSessionIndex][1]==0){
                            Map<String, String> map = new LinkedHashMap<>();
                            map.put("course", course);
                            map.put("teacher", teacher);
                            map.put("site", site);
                            map.put("week_of_session", weekOfSession[weekOfSessionIndex][0]+"");
                            map.put("day_of_week", dayOfWeek + "");
                            map.put("time",timeNO+"");
                            result.add(map);
                        } else {

                            for(int m=weekOfSession[weekOfSessionIndex][0]; m<=weekOfSession[weekOfSessionIndex][1]; m++){
                                Map<String, String> map = new LinkedHashMap<>();
                                map.put("course", course);
                                map.put("teacher", teacher);
                                map.put("site", site);
                                map.put("week_of_session", m+"");
                                map.put("day_of_week", dayOfWeek + "");
                                map.put("time",timeNO+"");
                                result.add(map);
                            }

                        }
                        weekOfSessionIndex++;


                    }
                } else {
                    //Storage the course with no time
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("course", course);
                    map.put("teacher", teacher);
                    map.put("site", site);
                    map.put("week_of_session", "0");
                    map.put("day_of_week", "0");
                    map.put("time","0");
                    result.add(map);
                }

                i = i + 9;
            }


        }

        return result;
    }*/

    /*
     * Parse teacher's basis lesson information
     * @param String html
     * return List<CourseBasisData>
     */
    /*
    public List<CourseBasisData> parseTeachersBasisInformation(String html){

        html = html.replaceAll("&nbsp;", " ");			//replace all "&nbsp;" to space
        Document doc = Jsoup.parse(html);

        List<CourseBasisData> courseBasisDataList = new ArrayList<>();

        Elements elements = doc.getElementsByTag("span");
        int elementsSize = elements.size();
        int elementsIndex = 1;

        String courseID;
        String course;
        String type;
        String teacher;
        String weight;



        while (elementsIndex + 2 < elementsSize) {

            boolean repeat = false;

            courseID = elements.get(elementsIndex).text();

            //Judge the id is repeated or not
            for(CourseBasisData courseBasisData : courseBasisDataList) {
                if(courseBasisData.getCourseId().equals(courseID)) {
                    repeat = true;
                    break;
                }
            }

            //If the course id is not repeat and insert the course data to courseBasisDataList
            if(!repeat) {

                course = elements.get(elementsIndex + 1).text();
                type = elements.get(elementsIndex + 2).text();
                teacher = elements.get(elementsIndex + 8).text();
                weight = elements.get(elementsIndex + 3).text();

                CourseBasisData courseBasisData = new CourseBasisData(
                        courseID,
                        course,
                        teacher,
                        weight,
                        type
                );

                courseBasisDataList.add(courseBasisData);
            }

            elementsIndex = elementsIndex + 9;
        }

        return courseBasisDataList;

    }
*/

}

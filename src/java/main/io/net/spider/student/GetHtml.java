package java.main.io.net.spider.student;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 刘乔泓 on 2015/10/13.
 * 登陆与获取信息页面
 * 构造方法传入学号和子系统密码
 */
@SuppressWarnings("deprecation")
public class GetHtml {
    private static String html = null;
    private static String randomNumber = null;
    private static String cookie = null;
    private static String kcookie = null;
    private static String ycookie = null;
    private static HttpResponse mr = null;
    private static String UerCode;
    private static String UerPwd;

    public boolean logStatus;

    /*
     *传入学号和子系统密码
     */
    public GetHtml(String UerCode, String UerPwd) {
        this.UerCode = UerCode;
        this.UerPwd = UerPwd;
        logStatus = login();
    }

    //不需要登陆子系统的时候使用
    public GetHtml() {
    }

    private static String s4() {
        int number = (int) ((1 + Math.random()) * 0x10000);
        String ngid = Integer.toString(number, 16).substring(1);
        return ngid;
    }

    private static String newGuid() {
        return (s4() + s4() + "-" + s4() + "-" + s4() + "-" + s4() + "-" + s4() + s4() + s4());
    }

    /*
     *获取老师课表（不需要登陆）
     * 传入教师名字，反回课表html
     */
    public String getTeacherLessonHtml(String teacherName) {
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        HttpPost httpPost = new HttpPost("http://202.192.240.54/kkcx/default.aspx");
        //请求实体
        HttpEntity httpEntity = null;
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("ctl00_TreeView1_ExpandState", "ennnnnnn"));
        parameters.add(new BasicNameValuePair("ctl00_TreeView1_SelectedNode", "ctl00_TreeView1t0"));
        parameters.add(new BasicNameValuePair("__EVENTTARGET", ""));
        parameters.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
        parameters.add(new BasicNameValuePair("ctl00_TreeView1_PopulateLog", ""));
        parameters.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUJODA1MTU4MTc3D2QWAmYPZBYCAgMPZBYGAgEPDxYCHgRUZXh0BSEyMDE1LTIwMTblrablubQ8YnIgLz7nrKzkuIDlrabmnJ9kZAIDDzwrAAkCAA8WCB4NTmV2ZXJFeHBhbmRlZGQeC18hRGF0YUJvdW5kZx4MU2VsZWN0ZWROb2RlBRFjdGwwMF9UcmVlVmlldzF0MB4JTGFzdEluZGV4AghkCBQrAAIFAzA6MBQrAAIWDB8ABQzlvIDor77mn6Xor6IeC05hdmlnYXRlVXJsBRIva2tjeC9kZWZhdWx0LmFzcHgeCERhdGFQYXRoBRIva2tjeC9kZWZhdWx0LmFzcHgeCURhdGFCb3VuZGceCFNlbGVjdGVkZx4IRXhwYW5kZWRnFCsACAUbMDowLDA6MSwwOjIsMDozLDA6NCwwOjUsMDo2FCsAAhYKHwAFDOePree6p+ivvuihqB8FBQ8va2tjeC9iamtiLmFzcHgfBgUPL2trY3gvYmprYi5hc3B4HwdnHwlnZBQrAAIWCh8ABQzpmaLns7vlvIDor74fBQUPL2trY3gveXhray5hc3B4HwYFDy9ra2N4L3l4a2suYXNweB8HZx8JZ2QUKwACFgofAAUJ5YWs6YCJ6K++HwUFDi9ra2N4L2d4ay5hc3B4HwYFDi9ra2N4L2d4ay5hc3B4HwdnHwlnZBQrAAIWCh8ABQnpgJror4bor74fBQUOL2trY3gvdHNrLmFzcHgfBgUOL2trY3gvdHNrLmFzcHgfB2cfCWdkFCsAAhYKHwAFDOaooeeziuafpeivoh8FBQ8va2tjeC9taGN4LmFzcHgfBgUPL2trY3gvbWhjeC5hc3B4HwdnHwlnZBQrAAIWCh8ABRLor77nqIvpgInor77lkI3ljZUfBQUPL2trY3gveGttZC5hc3B4HwYFDy9ra2N4L3hrbWQuYXNweB8HZx8JZ2QUKwACFgofAAUY5o6S6K++5pWZ5a6k5Y2g55So56S65oSPHwUFCy9jbGFzc3Jvb20vHwYFCy9jbGFzc3Jvb20vHwdnHwlnZGQCBw9kFgQCAQ9kFgRmD2QWAmYPZBYCAgEPDxYCHwAFFeivt+mAieaLqeafpeivouadoeS7tmRkAgMPZBYCAgEPZBYCAgEPEA8WAh8CZ2QQFUkM5Lqn5ZOB6K6+6K6hG+eUteawlOW3peeoi+WPiuWFtuiHquWKqOWMlgznlLXlrZDllYbliqES55S15a2Q5L+h5oGv5bel56iLLeeUteWtkOS/oeaBr+W3peeoi++8iOWNiuWvvOS9k+e7v+iJsuWFiea6kO+8iSTnlLXlrZDkv6Hmga/lt6XnqIvvvIjlhYnnlLXlt6XnqIvvvIkk55S15a2Q5L+h5oGv5bel56iL77yI5L+h5oGv5a6J5YWo77yJDOWvueWkluaxieivrQbms5XlraYY5rOV5a2m77yI56S+5Lya5bel5L2c77yJDOe6uue7h+W3peeoiyTnurrnu4flt6XnqIvvvIjnurrnu4fmnI3oo4XotLjmmJPvvIkt57q657uH5bel56iL77yI57q657uH5YyW5a2m5LiO5riF5rSB55Sf5Lqn77yJJ+e6uue7h+W3peeoi++8iOe6uue7h+acuueUteS4gOS9k+WMlu+8iSrnurrnu4flt6XnqIvvvIjpnZ7nu4fpgKDmnZDmlpnkuI7lt6XnqIvvvIkY57q657uH5bel56iL77yI5p+T5pW077yJFeacjeijheiuvuiuoeS4juW3peeoizzmnI3oo4Xorr7orqHkuI7lt6XnqIvvvIjmnI3oo4XooajmvJTkuI7npL7kvJroiJ7ouYjmlZnogrLvvIkV5pyN6KOF5LiO5pyN6aWw6K6+6K6hDOW3peeoi+euoeeQhgzlt6XllYbnrqHnkIYe5bel5ZWG566h55CG77yI5peF5ri4566h55CG77yJDOW3peS4muiuvuiuoRXlm73pmYXnu4/mtY7kuI7otLjmmJMS5rGJ6K+t5Zu96ZmF5pWZ6IKyD+axieivreiogOaWh+WtphvmsYnor63oqIDmloflrabvvIjluIjojIPvvIkV5YyW5a2m5bel56iL5LiO5bel6Im6DOeOr+Wig+W3peeoiwznjq/looPorr7orqEe546v5aKD6K6+6K6h77yI5Lqn5ZOB6K6+6K6h77yJCeS8muiuoeWtphvkvJrorqHlrabvvIjlt6XnqIvpgKDku7fvvIkV5Lya6K6h5a2m77yI57K+566X77yJFeS8muiuoeWtpu+8iOWuoeiuoe+8iR7kvJrorqHlrabvvIjms6jlhozpgKDku7fluIjvvIkM5py65qKw5bel56iLGOacuuaisOW3peeoi+WPiuiHquWKqOWMlhjorqHnrpfmnLrnp5HlrabkuI7mioDmnK8J5bu6562R5a2mDOS6pOmAmuW3peeoiyHkuqTpgJrlt6XnqIvvvIjpgZPot6/kuI7moaXmooHvvIkq5Lqk6YCa5bel56iL77yI6L2o6YGT5Lqk6YCa6L2m6L6G5bel56iL77yJJ+S6pOmAmuW3peeoi++8iOi9qOmBk+S6pOmAmueUteawlOWMlu+8iSrkuqTpgJrlt6XnqIvvvIjovajpgZPkuqTpgJrov5DokKXnrqHnkIbvvIkn5Lqk6YCa5bel56iL77yI6L2o6YGT5Lqk6YCa6Ieq5Yqo5YyW77yJJ+S6pOmAmuW3peeoi++8iOS6pOmAmuaOp+WItuS4jueuoeeQhu+8iR7kuqTpgJrlt6XnqIvvvIjnianmtYHnrqHnkIbvvIkJ6YeR6J6N5a2mDOaXhea4uOeuoeeQhgbml6Xor60M6L2v5Lu25bel56iLDOWVhuWKoeiLseivrQznpL7kvJrlt6XkvZwM5biC5Zy66JCl6ZSAEuinhuinieS8oOi+vuiuvuiuoSHmlbDlrabkuI7lupTnlKjmlbDlrabvvIjluIjojIPvvIkM6YCa5L+h5bel56iLJ+mAmuS/oeW3peeoi++8iOiuoeeul+acuumAmuS/oee9kee7nO+8iSHpgJrkv6Hlt6XnqIvvvIjnianogZTnvZHlt6XnqIvvvIkM5Zyf5pyo5bel56iLDOe9kee7nOW3peeoixvoiJ7ouYjlrabvvIjnpL7kvJroiJ7ouYjvvIkb5L+h5oGv566h55CG5LiO5L+h5oGv57O757ufFeS/oeaBr+S4juiuoeeul+enkeWtpgzooYzmlL/nrqHnkIYM6Im65pyv6K6+6K6hHuiJuuacr+iuvuiuoe+8iOW5s+mdouiuvuiuoe+8iSToibrmnK/orr7orqHvvIjnpL7kvJroiJ7ouYjmlZnogrLvvIk25bqU55So5YyW5a2m77yI5Yac5Lqn5ZOB5a6J5YWo5qOA5rWL5LiO566h55CG5pa55ZCR77yJEuiLseivre+8iOe/u+ivke+8iRLoi7Hor63vvIjluIjojIPvvIkJ6Ieq5Yqo5YyWFUkM5Lqn5ZOB6K6+6K6hG+eUteawlOW3peeoi+WPiuWFtuiHquWKqOWMlgznlLXlrZDllYbliqES55S15a2Q5L+h5oGv5bel56iLLeeUteWtkOS/oeaBr+W3peeoi++8iOWNiuWvvOS9k+e7v+iJsuWFiea6kO+8iSTnlLXlrZDkv6Hmga/lt6XnqIvvvIjlhYnnlLXlt6XnqIvvvIkk55S15a2Q5L+h5oGv5bel56iL77yI5L+h5oGv5a6J5YWo77yJDOWvueWkluaxieivrQbms5XlraYY5rOV5a2m77yI56S+5Lya5bel5L2c77yJDOe6uue7h+W3peeoiyTnurrnu4flt6XnqIvvvIjnurrnu4fmnI3oo4XotLjmmJPvvIkt57q657uH5bel56iL77yI57q657uH5YyW5a2m5LiO5riF5rSB55Sf5Lqn77yJJ+e6uue7h+W3peeoi++8iOe6uue7h+acuueUteS4gOS9k+WMlu+8iSrnurrnu4flt6XnqIvvvIjpnZ7nu4fpgKDmnZDmlpnkuI7lt6XnqIvvvIkY57q657uH5bel56iL77yI5p+T5pW077yJFeacjeijheiuvuiuoeS4juW3peeoizzmnI3oo4Xorr7orqHkuI7lt6XnqIvvvIjmnI3oo4XooajmvJTkuI7npL7kvJroiJ7ouYjmlZnogrLvvIkV5pyN6KOF5LiO5pyN6aWw6K6+6K6hDOW3peeoi+euoeeQhgzlt6XllYbnrqHnkIYe5bel5ZWG566h55CG77yI5peF5ri4566h55CG77yJDOW3peS4muiuvuiuoRXlm73pmYXnu4/mtY7kuI7otLjmmJMS5rGJ6K+t5Zu96ZmF5pWZ6IKyD+axieivreiogOaWh+WtphvmsYnor63oqIDmloflrabvvIjluIjojIPvvIkV5YyW5a2m5bel56iL5LiO5bel6Im6DOeOr+Wig+W3peeoiwznjq/looPorr7orqEe546v5aKD6K6+6K6h77yI5Lqn5ZOB6K6+6K6h77yJCeS8muiuoeWtphvkvJrorqHlrabvvIjlt6XnqIvpgKDku7fvvIkV5Lya6K6h5a2m77yI57K+566X77yJFeS8muiuoeWtpu+8iOWuoeiuoe+8iR7kvJrorqHlrabvvIjms6jlhozpgKDku7fluIjvvIkM5py65qKw5bel56iLGOacuuaisOW3peeoi+WPiuiHquWKqOWMlhjorqHnrpfmnLrnp5HlrabkuI7mioDmnK8J5bu6562R5a2mDOS6pOmAmuW3peeoiyHkuqTpgJrlt6XnqIvvvIjpgZPot6/kuI7moaXmooHvvIkq5Lqk6YCa5bel56iL77yI6L2o6YGT5Lqk6YCa6L2m6L6G5bel56iL77yJJ+S6pOmAmuW3peeoi++8iOi9qOmBk+S6pOmAmueUteawlOWMlu+8iSrkuqTpgJrlt6XnqIvvvIjovajpgZPkuqTpgJrov5DokKXnrqHnkIbvvIkn5Lqk6YCa5bel56iL77yI6L2o6YGT5Lqk6YCa6Ieq5Yqo5YyW77yJJ+S6pOmAmuW3peeoi++8iOS6pOmAmuaOp+WItuS4jueuoeeQhu+8iR7kuqTpgJrlt6XnqIvvvIjnianmtYHnrqHnkIbvvIkJ6YeR6J6N5a2mDOaXhea4uOeuoeeQhgbml6Xor60M6L2v5Lu25bel56iLDOWVhuWKoeiLseivrQznpL7kvJrlt6XkvZwM5biC5Zy66JCl6ZSAEuinhuinieS8oOi+vuiuvuiuoSHmlbDlrabkuI7lupTnlKjmlbDlrabvvIjluIjojIPvvIkM6YCa5L+h5bel56iLJ+mAmuS/oeW3peeoi++8iOiuoeeul+acuumAmuS/oee9kee7nO+8iSHpgJrkv6Hlt6XnqIvvvIjnianogZTnvZHlt6XnqIvvvIkM5Zyf5pyo5bel56iLDOe9kee7nOW3peeoixvoiJ7ouYjlrabvvIjnpL7kvJroiJ7ouYjvvIkb5L+h5oGv566h55CG5LiO5L+h5oGv57O757ufFeS/oeaBr+S4juiuoeeul+enkeWtpgzooYzmlL/nrqHnkIYM6Im65pyv6K6+6K6hHuiJuuacr+iuvuiuoe+8iOW5s+mdouiuvuiuoe+8iSToibrmnK/orr7orqHvvIjnpL7kvJroiJ7ouYjmlZnogrLvvIk25bqU55So5YyW5a2m77yI5Yac5Lqn5ZOB5a6J5YWo5qOA5rWL5LiO566h55CG5pa55ZCR77yJEuiLseivre+8iOe/u+ivke+8iRLoi7Hor63vvIjluIjojIPvvIkJ6Ieq5Yqo5YyWFCsDSWdnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dkZAIDDxYCHgdWaXNpYmxlaBYCZg9kFgJmD2QWBAIDDxQrAAJkZGQCBQ8UKwACZGRkGAMFHl9fQ29udHJvbHNSZXF1aXJlUG9zdEJhY2tLZXlfXxYoBQ9jdGwwMCRUcmVlVmlldzEFHmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkY2JYUQUdY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR4cTEFHWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkeHEyBR1jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHhxMwUdY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR4cTQFHWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkeHE1BR1jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHhxNgUeY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRjYkpDBR1jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGpjMQUdY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRqYzIFHWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkamMzBR1jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGpjNAUdY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRqYzUFHmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkY2JaQwUdY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR6YzEFHWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkemMyBR1jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHpjMwUdY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR6YzQFHWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkemM1BR1jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHpjNgUdY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR6YzcFHWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkemM4BR1jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHpjOQUeY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR6YzEwBR5jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHpjMTEFHmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkemMxMgUeY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR6YzEzBR5jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHpjMTQFHmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkemMxNQUeY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR6YzE2BR5jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJHpjMTcFHmN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkemMxOAUeY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSR6YzE5BSBjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGNiU0tCSgUeY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRjYlpZBSBjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGNiS0NESAUgY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRjYktDTUMFIGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkY2JSS0xTBSBjdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGNiU0tERAUlY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRMaXN0Vmlld1hLSA9nZAUlY3RsMDAkQ29udGVudFBsYWNlSG9sZGVyMSRMaXN0Vmlld1hLUQ9nZMDXsUmwHFRy3UvrcLpI0Fc4ELOQ"));
        parameters.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "0AE10C90"));
        parameters.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEWdwLN7ZXgDQLQ4e3vDwKslYq7DwKplYq7DwKqlYq7DwKnlYq7DwKolYq7DwKllYq7DwK+4bXwDwLGr4nUCwLDr4nUCwLEr4nUCwLBr4nUCwLCr4nUCwLO4bXwDwL2wuanDgLzwuanDgL0wuanDgLxwuanDgLywuanDgLvwuanDgLwwuanDgLtwuanDgLuwuanDgL2wqamDgL2wqqmDgL2wq6mDgL2wrKmDgL2wpamDgL2wpqmDgL2wp6mDgL2wqKmDgL2wsamDgL2wsqmDgK9g636AQL1gKTJDQLO4Y3wDwLzlvPQDwKA9cLNCgL5xcVQAuaQyrgHAuvZuB4C07OGgQ0Cua/+aALqy7+rBQLFu9S+DQL3i9/UDgKJi9biCwK/raGmBQLwj4j1DQLfiLecBgKXhM7KCQKHhqjgDQK4guTFAgL1p4mlCAKFydvNDwKO6IbyDQKO6NKMDwKjz+SkDAKx+8ilCQLuqM+XBwLAi6/GBgKYoJuTDgLmi+XFCwKL/ZZzArzx5/AFAsu87L4OAsD8mRoCzOLMswkC5NXAjwoCx4G6oAgC+s+ToQkClf77hgYCiZiq8QoCn4XWnAMCiarhlg0CrNyMSAKj68K3AQLXlo3RAQLG4b6NDQKZ4MnXCALik9fvBwKv3u/PBwLUqI+BBgKKlZTUDwK1zt/SDgLuuMrSDAKVqfC6CAL86JXjBALhwu7GDQKNhPa2BALBnt/QAQLXw8qbCgKK9pzPAgLptuHrBALbxL3iBQKE34qtBQLsuIqtCgKui7riCwL3tLahDwKgivWcAwLLn5a7BwLnnaiLDAKUmP7rDAKXn7nwDwLnyJjdCwKk6sSNBAL5xpvcDwKHt5+CCwKMq9q6CAKDjLLEAwLLysrVDQKQ2dm5BgLEytbTCgLCwtecBQLcgMChAwKbjKKxBgLngPySDAKA4sljR1y/S5FwNj4B9MuGfKqBSZ/tvrY="));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtSKBJ", ""));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$ddlZY", "产品设计"));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtKCDH", ""));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtKCMC", ""));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$cbRKLS", "on"));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtRKJS", teacherName));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$txtSKDD", ""));
        parameters.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$Button1", "提交"));

        try {
            httpEntity = new UrlEncodedFormEntity(parameters, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(httpEntity);
        client.getConnectionManager().shutdown();
        HttpParams httpParams = new BasicHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);
        client = new DefaultHttpClient(httpParams);
        client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, false);
        HttpResponse response = null;
        //提交信息
        try {
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {     //获取成功
            //取得返回的html
            try {
                html = EntityUtils.toString(response.getEntity(), "gb2312");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            html = null;
        }
        client.getConnectionManager().shutdown();
        return html;
    }

    /*
     *登陆，返回布尔值，ture则登陆成功，false则登陆失败
     */
    public boolean login() {
        //获取验证码
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setIntParameter("http.socket.timeout", 10000);
        HttpGet get = new HttpGet("http://jwc.wyu.edu.cn/student/rndnum.asp"); // get
        String NGID = newGuid();
        ycookie = "NGID=" + NGID + ";" + NGID + "=http%3A//jwc.wyu.edu.cn/student/body.htm";
        get.setHeader("Cookie", ycookie);   //设置cookie
        HttpResponse response = null;
        //提交信息
        try {
            response = client.execute(get);
        } catch (ClientProtocolException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {     //获取验证码成功
            //拼凑cookie信息
            List<Cookie> cookies = client.getCookieStore().getCookies();
            if (cookies.size() <= 1) {
                return false;
            }
            String asp = cookies.get(0).getName();
            String aspn = cookies.get(0).getValue();
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(aspn);
            if (!matcher.matches()) {
                cookie = ycookie + ";" + asp + "=" + aspn + ";";
                randomNumber = cookies.get(1).getValue();
                kcookie = cookie + "LogonNumber=";
                cookie = cookie + "LogonNumber=" + randomNumber;
            } else {
                cookie = ycookie + ";" + cookies.get(1).getName() + "=" + cookies.get(1).getValue() + ";";
                randomNumber = aspn;
                kcookie = cookie + "LogonNumber=";
                cookie = cookie + "LogonNumber=" + randomNumber;
            }
        } else {
            return false;
        }
        //添加cookie
        // login
        HttpPost post = new HttpPost("http://jwc.wyu.edu.cn/student/logon.asp"); // login登陆页面
        // 构建表头
        String Submit = "%CC%E1+%BD%BB";
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("UserCode", UerCode));
        parameters.add(new BasicNameValuePair("UserPwd", UerPwd));
        parameters.add(new BasicNameValuePair("Validate", randomNumber));
        parameters.add(new BasicNameValuePair("Submit", Submit));
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(parameters, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        post.setEntity(entity);
        post.setHeader("Cookie", cookie); //设置标头cookies
        post.setHeader("Referer", "http://jwc.wyu.edu.cn/student/body.htm"); //标头
        client.getConnectionManager().shutdown();
        HttpParams httpParams = new BasicHttpParams();
        HttpClientParams.setRedirecting(httpParams, false);
        client = new DefaultHttpClient(httpParams);
        //提交登陆信息
        try {
            response = client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  //登陆成功
            //登陆验证
            client.getConnectionManager().shutdown();
            return true;
        } else {
            client.getConnectionManager().shutdown();
            return false;
        }
    }

    /*
     *获取课表，返回课表的html
     * 此方法必须在登陆后调用
     * 获取成功返回页面html，失败则返回null
     */
    public String getLessonHtml() {
        DefaultHttpClient mclient = new DefaultHttpClient();
        mclient.getParams().setIntParameter("http.socket.timeout", 10000);
        HttpGet mget = new HttpGet("http://jwc.wyu.edu.cn/student/f3.asp");
        mget.setHeader("Cookie", kcookie);
        mclient.getConnectionManager().shutdown();
        HttpParams mhttpParams = new BasicHttpParams();
        HttpClientParams.setRedirecting(mhttpParams, false);
        mclient = new DefaultHttpClient(mhttpParams);
        try {
            mr = mclient.execute(mget);  //请求课表页
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mr != null && mr.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {     //获取成功
            //取得返回的html
            try {
                html = EntityUtils.toString(mr.getEntity(), "gb2312");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mclient.getConnectionManager().shutdown();
        }
        return html;
    }

    /*
     *获取成绩单
     * 此方法必须在登陆后调用
     * 获取成功返回页面html，失败则返回null
     */
    public String getScoreHtml() {
        String scohtml = null;
        DefaultHttpClient client = new DefaultHttpClient();
        DefaultHttpClient client1 = new DefaultHttpClient();
        DefaultHttpClient client2 = new DefaultHttpClient();
        //获取成绩单的前提
        HttpGet get = new HttpGet("http://jwc.wyu.edu.cn/student/createsession_a.asp");
        HttpGet get1 = new HttpGet("http://jwc.wyu.edu.cn/student/createsession_b.asp");
        HttpGet get2 = new HttpGet("http://jwc.wyu.edu.cn/student/f4_myscore.asp");
        //写入cookie和必要标头
        get.setHeader("Cookie", kcookie);
        get1.setHeader("Cookie", kcookie);
        get2.setHeader("Cookie", kcookie);
        get2.setHeader("Referer", "http://jwc.wyu.edu.cn/student/menu.asp");
        HttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response = client1.execute(get1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.getConnectionManager().shutdown();
        client1.getConnectionManager().shutdown();
        try {
            response = client2.execute(get2); //获取成绩单页面
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  //获取成功

            try {
                scohtml = EntityUtils.toString(response.getEntity(), "gb2312");
            } catch (IOException e) {
                e.printStackTrace();
            }
            client2.getConnectionManager().shutdown();
            return scohtml;
        }
        client2.getConnectionManager().shutdown();
        return scohtml;
    }

    /*
     * 获取学生详情
     * 此方法必须在登陆后调用
     * 获取成功返回页面html，失败则返回null
     */
    public String getStuInfoHtml() {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet("http://jwc.wyu.edu.cn/student/f1.asp");
        get.setHeader("Cookie", kcookie);
        HttpResponse response = null;
        try {
            response = client.execute(get);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String stuInfoHtml = null;
            try {
                stuInfoHtml = EntityUtils.toString(response.getEntity(), "gb2312");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stuInfoHtml;
        } else {
            return null;
        }
    }
}

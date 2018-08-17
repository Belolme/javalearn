package java.main.io.net.retrofit;

import retrofit2.Call;

import java.io.IOException;
import java.util.Calendar;

/**
 * Test class for retrofit
 * <p/>
 * Created by billin on 16-9-21.
 */
public class RetrofitTest {

    public static void main(String[] args) throws IOException {

        User user = new User(0,
                "whajklk",
                1,
                "123@qq.com",
                "11568432",
                Calendar.getInstance().getTimeInMillis(),
                "guangzhou",
                "12345678",
                null);


        CotogoServer cotogoServer = RetrofitClient.createService(CotogoServer.class);
        Call<String> userRegisted = cotogoServer.getUser(44);
        String json = userRegisted.execute().body();
        System.out.println(JsonResolver.resolve(json, User.class));

    }

}

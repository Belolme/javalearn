package javax.main.io.net.retrofit;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * First demo of retrofit
 * <p/>
 * Created by billin on 16-9-20.
 */
public interface CotogoServer {

    @POST("registerUser.action")
    Call<String> register(@Body User user);

    @GET("getUserById.action")
    Call<String> getUser(@Query("id") int id);

}

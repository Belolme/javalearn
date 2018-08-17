package java.main.io.net.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit wrapper
 * <p/>
 * Created by billin on 16-9-20.
 */
public class RetrofitClient {

    private static final String BASE_URL = "http://119.29.215.188:80/cotogo/";

    private static Retrofit.Builder mBuilder
            = new Retrofit.Builder().baseUrl(BASE_URL);

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = mBuilder
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }
}

package retrofit;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Compile json to object or exception object
 * <p/>
 * Created by billin on 16-9-21.
 */
public class JsonResolver {

    public static <D> ReturnObject<D> resolve(String json) {

        Type type = new TypeToken<ReturnObject<D>>() {
        }.getType();
        return new Gson().fromJson(json, type);

    }

}

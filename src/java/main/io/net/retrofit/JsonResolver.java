package java.main.io.net.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Compile json to object or exception object
 * <p/>
 * Created by billin on 16-9-21.
 */
public class JsonResolver {

    public static <D> ReturnObject<D> resolve(String json, Class<D> clazz) {

        Gson gson = new Gson();
        Type type = new TypeToken<ReturnObject<D>>() {}.getType();
        ReturnObject<D> returnObject = gson.fromJson(json, type);

        JsonObject data = new JsonParser().parse(json).getAsJsonObject().getAsJsonObject("data");
        returnObject.setData(gson.fromJson(data, clazz));

        return returnObject;
    }

}

package backend.drivor.base.domain.utils;

import com.google.gson.Gson;

public class GsonSingleton {

    private static Gson gson = new Gson();

    public static Gson getInstance() {
        if (gson == null)
            gson = new Gson();
        return gson;
    }
}

package backend.drivor.base.domain.utils;

public class CastTypeUtils {

    public static Long toLong(String s) {
           return Long.parseLong(s);
    }

    public static Long toLong(Double d) {
        Long l = (new Double(d)).longValue();
        return l;
    }

}

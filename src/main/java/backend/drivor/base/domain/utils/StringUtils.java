package backend.drivor.base.domain.utils;

import org.springframework.lang.Nullable;

public class StringUtils {

    public static boolean isEmpty(String... arrs) {
        for (String s : arrs) {
            if (org.springframework.util.StringUtils.isEmpty(s)) return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String... arrs) {
        return !isEmpty(arrs);
    }

    public static boolean isInteger(String... args) {
        for (String s : args) {
            try {
                Integer.parseInt(s);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasText(@Nullable String str) {
        return str != null && !str.isEmpty() && org.springframework.util.StringUtils.hasText(str);
    }

    public static String removeSpace(String value) {
        if (isEmpty(value))
            return value;
        return value.trim().replace(" ", "");
    }

    public static String trim(String value) {
        if (isEmpty(value))
            return value;
        return value.trim();
    }
}

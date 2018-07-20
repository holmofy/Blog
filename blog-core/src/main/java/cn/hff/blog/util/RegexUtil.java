package cn.hff.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具
 * <p>
 * Created by Holmofy on 2018/6/21.
 */
public final class RegexUtil {

    private static final String REGEX_EMAIL = "^\\w+@\\w+\\.\\w+$";

    private static final String REGEX_PHONE = "^1[34578]\\d{9}$";

    private static final Pattern PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);

    private static final Pattern PATTERN_PHONE = Pattern.compile(REGEX_PHONE);

    public static boolean isEmail(CharSequence chars) {
        Matcher matcher = PATTERN_EMAIL.matcher(chars);
        return matcher.matches();
    }

    public static boolean isPhone(CharSequence chars) {
        Matcher matcher = PATTERN_PHONE.matcher(chars);
        return matcher.matches();
    }

}

package com.yuas.commerce.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liqing on 2018/8/28.
 */

public class PatternMatchUtils {
    private static final String REGEX_EMAIL = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    private static final Pattern emailPattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    private static final String REGEX_PHONE = "^(1)[0-9]{10}$";
    private static final Pattern phonePattern = Pattern.compile("^(1)[0-9]{10}$");

    public PatternMatchUtils() {
    }

    public static boolean isEmail(String var0) {
        if(var0 == null) {
            return false;
        } else {
            Matcher var1 = emailPattern.matcher(var0);
            return var1.matches();
        }
    }

    public static boolean isMobile(String var0) {
        if(var0 == null) {
            return false;
        } else {
            Matcher var1 = phonePattern.matcher(var0);
            return var1.matches();
        }
    }

    public static boolean isPrice(String var0) {
        if(var0 == null) {
            return false;
        } else {
            String var1 = "^(?!0\\d)(?!\\.)[0-9]+(\\.[0-9]{1,2})?$";
            Pattern var2 = Pattern.compile(var1);
            Matcher var3 = var2.matcher(var0);
            return var3.matches();
        }
    }

    public static boolean isPost(String var0) {
        if(var0 == null) {
            return false;
        } else {
            String var1 = "^[1-9][0-9]{5}$";
            Pattern var2 = Pattern.compile(var1);
            Matcher var3 = var2.matcher(var0);
            return var3.matches();
        }
    }
}


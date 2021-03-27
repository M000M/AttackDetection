package cn.edu.pku.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static SimpleDateFormat simpleDateFormat = null;

    public static String formatTime(Date date) {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        return simpleDateFormat.format(date);
    }
}

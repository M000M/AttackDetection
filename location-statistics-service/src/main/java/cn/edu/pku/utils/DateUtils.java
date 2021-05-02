package cn.edu.pku.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public static String fromDateToString(Date date) {
        return dateFormat.format(date);
    }
}

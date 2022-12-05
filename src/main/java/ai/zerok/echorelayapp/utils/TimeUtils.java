package ai.zerok.echorelayapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String getFormattedDate(){
        return DATE_FORMATTER.format(new Date());
    }
}

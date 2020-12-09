package utility.database;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private static SimpleDateFormat sdf= new SimpleDateFormat("yyyy年MM月dd日");
    private static SimpleDateFormat dbDateSdf= new SimpleDateFormat("yyyy-MM-dd");
    public static String simpleDate(Date date){
        return  sdf.format(date);
    }
    public static String DBDate(Date date){
        return dbDateSdf.format(date);
    }
}

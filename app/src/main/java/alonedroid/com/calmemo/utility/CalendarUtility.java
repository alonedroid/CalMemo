package alonedroid.com.calmemo.utility;

import android.content.Context;

import org.androidannotations.annotations.EBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@EBean
public class CalendarUtility {

    private Calendar calendar;

    public CalendarUtility(Context context) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(new Date());
    }

    public void offsetMonthToday(int offset) {
        this.calendar.setTime(new Date());
        this.calendar.add(Calendar.MONTH, offset);
    }

    public void offsetDate(int weekOfMonth, int dayOfWeek) {
        this.calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
        this.calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    }

    public String getYYYY() {
        return getDateFromFormat("yyyy");
    }

    public String getMM() {
        return getDateFromFormat("MM");
    }

    public String getDD(int weekOfMonth, int dayOfWeek) {
        offsetDate(weekOfMonth, dayOfWeek);
        return getDateFromFormat("dd");
    }

    public int getFirstDayOfWeek() {
        this.calendar.set(Calendar.DAY_OF_MONTH, 1);
        return this.calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getLastDayOfMonth() {
        return this.calendar.getActualMaximum(Calendar.DATE);
    }

    private String getDateFromFormat(String format) {
        return new SimpleDateFormat(format).format(this.calendar.getTime());
    }

    public void resetBase(String year, String month) {
        this.calendar.set(toInt(year), toInt(month) - 1, 1);
    }

    private int toInt(String str) {
        return Integer.parseInt(str);
    }
}

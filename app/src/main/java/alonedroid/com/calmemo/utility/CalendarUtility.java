package alonedroid.com.calmemo.utility;

import android.content.Context;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alonedroid.com.calmemo.realm.CmPhoto;

@EBean
public class CalendarUtility {

    @RootContext
    Context context;

    @StringRes
    String cmYear;

    @StringRes
    String cmMonth;

    @StringRes
    String cmDay;

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
        return getDateFromFormat(this.cmYear);
    }

    public String getMM() {
        return getDateFromFormat(this.cmMonth);
    }

    public String getDD() {
        return getDateFromFormat(this.cmDay);
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

    public void setDateTime(String datetime) {
        if (datetime == null) return;

        Matcher m = Pattern.compile("(\\d+).(\\d+).(\\d+).(\\d+).(\\d+).(\\d+)").matcher(datetime);
        if (m.find()) {
            this.calendar.set(toInt(m.group(1))
                    , toInt(m.group(2)) - 1
                    , toInt(m.group(3))
                    , toInt(m.group(4))
                    , toInt(m.group(5))
                    , toInt(m.group(6)));
        }
    }

    public void setDate(String date) throws ParseException {
        this.calendar.setTime(new SimpleDateFormat(CmPhoto.CM_DATE_FORMAT).parse(date));
    }

    public String getDate() {
        return getDateFromFormat(CmPhoto.CM_DATE_FORMAT);
    }

    public String getTime() {
        return getDateFromFormat(CmPhoto.CM_TIME_FORMAT);
    }

    public int compareMonth(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(new SimpleDateFormat(CmPhoto.CM_DATE_FORMAT).parse(date));

        int baseYear = toInt(getDateFromFormat(this.cmYear));
        int baseMonth = toInt(getDateFromFormat(this.cmMonth));
        int newYear = toInt(new SimpleDateFormat(this.cmYear).format(c.getTime()));
        int newMonth = toInt(new SimpleDateFormat(this.cmMonth).format(c.getTime()));

        return ((baseYear - newYear) * 12) + (baseMonth - newMonth);
    }

    public CalendarUtility newInstance(String date) throws ParseException {
        CalendarUtility_ util = CalendarUtility_.getInstance_(this.context);
        util.setDate(date);
        return util;
    }
}

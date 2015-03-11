package alonedroid.com.calmemo.scene.calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CmCalendarAdapter extends FragmentStatePagerAdapter {
    private int mDisplayYears;
    private int mFlatPosition;
    private Calendar mCalendar;

    public CmCalendarAdapter(FragmentManager fm) {
        super(fm);
        this.mCalendar = Calendar.getInstance();
    }

    @Override
    public Fragment getItem(int position) {
        if (this.mFlatPosition == 0) {
            calculateFlatPosition();
        }

        // 中央位置からの差分で対象の年月を計算する
        this.mCalendar.setTime(new Date());
        this.mCalendar.add(Calendar.MONTH, position - this.mFlatPosition);
        String display_date = new SimpleDateFormat("yyyyMM").format(this.mCalendar.getTime());
        CmCalendarFragment fragment = CmCalendarFragment.newInstance(display_date);
        return fragment;
    }

    @Override
    public int getCount() {
        // 前後10年分を総数とする
        return this.mDisplayYears * 12 * 2;
    }

    void setDisplayYears(int display_years) {
        this.mDisplayYears = display_years;
    }

    int calculateFlatPosition() {
        this.mFlatPosition = this.mDisplayYears * 12 * 2 / 2;
        return getFlatPosition();
    }

    int getFlatPosition() {
        return this.mFlatPosition;
    }
}
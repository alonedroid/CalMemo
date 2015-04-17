package alonedroid.com.calmemo.scene.calendar;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.utility.CalendarUtility;
import alonedroid.com.calmemo.utility.CalendarUtility_;
import lombok.Getter;
import lombok.Setter;

public class CmCalendarAdapter extends FragmentStatePagerAdapter {

    @Setter
    private int pageCountYears;

    @Getter
    private String displayYear;

    @Getter
    private String displayMonth;

    @Getter
    private int basePosition;

    private CalendarUtility calendar;

    public CmCalendarAdapter(FragmentManager fm) {
        super(fm);
        this.calendar = CalendarUtility_.getInstance_(CmApplication.getContext());
    }

    @Override
    public Fragment getItem(int position) {
        if (this.basePosition == 0) {
            calculateBasePosition();
        }

        // 中央位置からの差分で対象の年月を計算する
        adjustCalendar(position);
        return CmCalendarChildFragment_.newInstance(this.displayYear, this.displayMonth);
    }

    @Override
    public int getCount() {
        // 前後10年分を総数とする
        return this.pageCountYears * 12 * 2;
    }

    int calculateBasePosition() {
        this.basePosition = this.pageCountYears * 12 * 2 / 2;
        return getBasePosition();
    }

    private void adjustCalendar(int position) {
        this.calendar.offsetMonthToday(position - this.basePosition);
        this.displayYear = this.calendar.getYYYY();
        this.displayMonth = this.calendar.getMM();
    }
}
package alonedroid.com.calmemo.scene.calendar;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionPixelSizeRes;
import org.androidannotations.annotations.res.IntegerRes;
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;

import java.text.ParseException;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.ViewFactory;
import alonedroid.com.calmemo.activity.CmGalleryActivity;
import alonedroid.com.calmemo.utility.CalendarUtility;
import alonedroid.com.calmemo.view.CmDateView;

@EFragment(R.layout.fragment_cm_calendar)
public class CmCalendarFragment extends Fragment {

    private static final int REQUEST_CODE = 1001;

    @Bean
    ViewFactory factory;

    @Bean
    CalendarUtility calendarUtility;

    @StringRes
    String resultDate;

    @IntegerRes
    int displayYears;

    @IntegerRes
    int displayCol;

    @DimensionPixelSizeRes
    int dimenFrame;

    @StringArrayRes
    String[] dateStrings;

    TypedArray dateColors;

    @ViewById
    TextView cmCalendarYmView;

    @ViewById
    LinearLayout cmCalendarDayOfWeekView;

    @ViewById
    ViewPager cmCalendarPager;

    private CmCalendarAdapter adapter;

    private int basePosition;

    @AfterInject
    void onAfterInject() {
        this.dateColors = getResources().obtainTypedArray(R.array.date_colors);
    }

    @AfterViews
    void onAfterViews() {
        initPager();
        initCalendarYmView();
        initDayOfWeek();
    }

    private void initPager() {
        // 前後何年を表示するのか、初期値を設定する
        this.adapter = new CmCalendarAdapter(getFragmentManager());
        this.adapter.setPageCountYears(this.displayYears);
        this.cmCalendarPager.setAdapter(this.adapter);

        // 初期位置を中央に持っていき、前後スワイプ可能にする
        this.basePosition = this.adapter.calculateBasePosition();
        this.cmCalendarPager.setCurrentItem(this.basePosition);

        this.cmCalendarPager.setOnPageChangeListener(onPageChangedListener());
    }

    private ViewPager.OnPageChangeListener onPageChangedListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                CmCalendarFragment.this.calendarUtility.offsetMonthToday(i - CmCalendarFragment.this.basePosition);
                setCmCalendarYmView();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };
    }

    private void initCalendarYmView() {
        this.calendarUtility.offsetMonthToday(0);
        setCmCalendarYmView();
    }

    private void setCmCalendarYmView() {
        String calendarYm = this.calendarUtility.getYYYY() + " / " + this.calendarUtility.getMM();
        this.cmCalendarYmView.setText(calendarYm);
    }

    private void initDayOfWeek() {
        for (int i = 0; i < 7; i++) {
            CmDateView view = generateDateView(i);
            this.cmCalendarDayOfWeekView.addView(view);
        }
    }

    private CmDateView generateDateView(int index) {
        String date = this.dateStrings[index];
        int color = this.dateColors.getColor(index % 7, 1);
        int width = CmApplication.divideDisplayWidth(this.displayCol);
        int height = FrameLayout.LayoutParams.MATCH_PARENT;

        return this.factory.newCmDateView(date, color, null, width, height, this.dimenFrame);
    }

    @Click
    void albumImportIcon(View view) {
        startActivityForResult(CmGalleryActivity.newIntent(), REQUEST_CODE);
    }

    @Click
    void albumHintIcon(View view) {
        CmApplication.show("hint");
    }

    @OnActivityResult(REQUEST_CODE)
    void resultGallery(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        try {
            String date = data.getStringExtra(this.resultDate);
            int diffMonth = this.calendarUtility.compareMonth(date);
            int absMonth = Math.abs(diffMonth);
            if (absMonth > 2) return;

            int photoMonth = this.cmCalendarPager.getCurrentItem() + diffMonth;
            ((CmCalendarChildFragment_) this.adapter.instantiateItem(this.cmCalendarPager, photoMonth)).update(date);
        } catch (ParseException e) {

        }
    }

    public static CmCalendarFragment newInstance() {
        CmCalendarFragment_.FragmentBuilder_ builder_ = CmCalendarFragment_.builder();
        return builder_.build();
    }
}

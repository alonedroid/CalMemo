package alonedroid.com.calmemo.scene.calendar;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionPixelSizeRes;
import org.androidannotations.annotations.res.IntegerRes;

import java.util.List;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.ViewFactory;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.realm.RealmAccessor;
import alonedroid.com.calmemo.scene.album.CmAlbumActivity;
import alonedroid.com.calmemo.utility.BitmapUtility;
import alonedroid.com.calmemo.utility.CalendarUtility;
import alonedroid.com.calmemo.utility.StringUtility;
import alonedroid.com.calmemo.view.CmDateView;

@EFragment(R.layout.fragment_cm_calendar_child)
public class CmCalendarChildFragment extends Fragment {

    @Bean
    ViewFactory factory;

    @Bean
    StringUtility stringUtility;

    @Bean
    CalendarUtility calendarUtility;

    @Bean
    RealmAccessor accessor;

    @IntegerRes
    int displayRow;

    @IntegerRes
    int displayCol;

    @DimensionPixelSizeRes
    int dimenFrame;

    TypedArray dateColors;

    @ViewById
    LinearLayout cmCalendarRoot;

    @FragmentArg
    String argDisplayYear;

    @FragmentArg
    String argDisplayMonth;

    private List<CmPhoto> photoList;

    @AfterInject
    void onAfterInject() {
        this.calendarUtility.resetBase(this.argDisplayYear, this.argDisplayMonth);
        this.dateColors = getResources().obtainTypedArray(R.array.date_colors);
        this.photoList = this.accessor.getPhotosByDate(this.argDisplayYear, this.argDisplayMonth);
    }

    @AfterViews
    void onAfterViews() {
        initCalendar();
    }

    private void initCalendar() {
        final int firstPos = this.calendarUtility.getFirstDayOfWeek() - 1;
        final int last = this.calendarUtility.getLastDayOfMonth();
        int date = 1;

        LinearLayout weekRow = findViewByIndex(1);

        for (int i = 0; i < firstPos; i++) {
            CmDateView view = generateDateView(0, "");
            weekRow.addView(view);
        }

        for (int i = 0; i < last; i++) {
            final int row = (i + firstPos) / this.displayCol + 1;
            final int col = (i + firstPos) % this.displayCol;
            if (col == 0) {
                weekRow = findViewByIndex(row);
            }

            CmDateView view = generateDateView(col, String.valueOf(date++));
            view.setOnClickListener(this::onClickListener);
            weekRow.addView(view);
        }
    }

    private LinearLayout findViewByIndex(int index) {
        return (LinearLayout) this.cmCalendarRoot.findViewById(getResources().getIdentifier("cm_calendar_" + index, "id", getActivity().getPackageName()));
    }

    private CmDateView generateDateView(int col, String date) {
        String key = this.argDisplayYear + this.argDisplayMonth + date;
        Bitmap bitmap = getPhoto(key);
        int color = this.dateColors.getColor(col % 7, 1);
        int width = CmApplication.divideDisplayWidth(this.displayCol);
        int height = FrameLayout.LayoutParams.MATCH_PARENT;

        return this.factory.newCmDateView(date, color, bitmap, width, height, this.dimenFrame);
    }

    private Bitmap getPhoto(String date) {
        if (this.photoList == null) return null;

        for (CmPhoto photo : this.photoList) {
            if (photo.getCmDate().equals(date)) {
                return BitmapUtility.decodeBitmapString(photo.getCmPhoto());
            }
        }

        return null;
    }

    public void onClickListener(View view) {
        CmDateView dateView = (CmDateView) view;
        if (!dateView.hasImage()) return;

        String argDate = getYmd(dateView.getDate());
        Intent intent = CmAlbumActivity.newIntent(argDate);
        getActivity().startActivity(intent);
    }

    private String getYmd(String date) {
        return this.argDisplayYear + this.argDisplayMonth + stringUtility.format00(date);
    }

    public static CmCalendarChildFragment newInstance(String displayYear, String displayMonth) {
        CmCalendarChildFragment_.FragmentBuilder_ builder_ = CmCalendarChildFragment_.builder();
        builder_.argDisplayYear(displayYear);
        builder_.argDisplayMonth(displayMonth);
        return builder_.build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.accessor.close();
    }
}

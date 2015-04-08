package alonedroid.com.calmemo.scene.calendar;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntegerRes;

import java.util.Calendar;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.CmUtility;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.scene.album.CmAlbumActivity;
import alonedroid.com.calmemo.scene.cover.CmCoverFragment;
import alonedroid.com.calmemo.view.CmDateView;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

@EFragment(R.layout.fragment_mc_calendar)
public class CmCalendarFragment extends Fragment {

    private static final String ARG_DISPLAY_YEAR = "argDisplayYear";

    private static final String ARG_DISPLAY_MONTH = "argDisplayMonth";

    @IntegerRes
    int displayWeeksNum;

    @IntegerRes
    int displayDatesNum;

    @ViewById
    LinearLayout cmCalendarRoot;

    @ViewById
    TextView cmCalendarYm;

    @FragmentArg
    String argDisplayYear;

    @FragmentArg
    String argDisplayMonth;

    private String[][] mMonth;

    @DebugLog
    public static CmCalendarFragment newInstance(String display_year, String display_month) {
        Bundle args = new Bundle();
        args.putString(ARG_DISPLAY_YEAR, display_year);
        args.putString(ARG_DISPLAY_MONTH, display_month);

        CmCalendarFragment fragment = new CmCalendarFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    @AfterInject
    void onAfterInject() {
        mMonth = new String[this.displayWeeksNum][this.displayDatesNum];

        // 曜日を配列に格納
        String[] week_str = getResources().getStringArray(R.array.date_strings);
        for (int i = 0; i < week_str.length; i++) {
            this.mMonth[0][i] = week_str[i];
        }

        // 日付を配列に格納
        final Calendar cal = Calendar.getInstance();

        cal.set(Integer.parseInt(this.argDisplayYear), Integer.parseInt(this.argDisplayMonth) - 1, 1);
        for (int i = 0; i < 31; i++) {
            int week_no = cal.get(Calendar.WEEK_OF_MONTH);
            int date_no = cal.get(Calendar.DAY_OF_WEEK) - 1;
            this.mMonth[week_no][date_no] = String.valueOf(cal.get(Calendar.DATE));
            cal.add(Calendar.DATE, 1);
        }
    }

    @DebugLog
    @AfterViews
    void onAfterViews() {
        this.cmCalendarYm.setText(this.argDisplayYear + " / " + this.argDisplayMonth);

        CmOnClickListener listener = new CmOnClickListener();
        TypedArray color_array = getResources().obtainTypedArray(R.array.date_colors);

        final int right_margin = 1;
        for (int i = 0; i < this.displayDatesNum; i++) {
            LinearLayout ll_week = (LinearLayout) this.cmCalendarRoot.findViewById(getResources().getIdentifier("cm_calendar_" + i, "id", getActivity().getPackageName()));

            int left_width = CmApplication.mDisplayWidth;
            for (int j = 0; j < this.displayWeeksNum; j++) {
                int width = left_width / (this.displayWeeksNum - j);
                left_width -= width;
                CmDateView cv = new CmDateView(getActivity());
                cv.setDate(this.mMonth[i][j]);
                cv.setDateColor(color_array.getColor(j % 7, 1));
                cv.setDateImage(getPhoto(this.argDisplayYear + this.argDisplayMonth + String.format("%2s", this.mMonth[i][j]).replace(" ", "0")));
                cv.setLayoutParams(new LinearLayout.LayoutParams(width, FrameLayout.LayoutParams.MATCH_PARENT));
                cv.setOnClickListener(listener);
                if (j + 1 < this.displayWeeksNum) {
                    cv.setPadding(0, 0, right_margin, 0);
                }
                ll_week.addView(cv);
            }
        }
    }

    public Bitmap getPhoto(String date) {
        Realm realm = Realm.getInstance(getActivity(), getString(R.string.realm_instance));

        RealmQuery<CmPhoto> query = realm.where(CmPhoto.class);
        query.equalTo(CmPhoto.CM_DATE, date);
        RealmResults<CmPhoto> resultAll = query.findAll();
        RealmResults<CmPhoto> result =
                realm.where(CmPhoto.class)
                        .equalTo(CmPhoto.CM_DATE, date)
                        .notEqualTo(CmPhoto.CM_PHOTO, "")
//                        .or()
//                        .equalTo("name", "Chip")
                        .findAll();

//        RealmResults<CmPhoto> sortedAscending  = result.sort("age");
//
//        RealmResults<CmPhoto> sortedDescending =
//                result.sort("age", RealmResults.SORT_ORDER_DECENDING);

        if (1 <= result.size()) {
            return CmUtility.decodeBitmapString(result.get(0).getCm_photo());
        } else {
            return null;
        }

    }

    private class CmOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            CmDateView view = (CmDateView) v;
            Intent intent = new Intent(getActivity(), CmAlbumActivity.class);
            intent.putExtra(CmAlbumActivity.ARG_DISPLAY_DATE, view.getDate());
            startActivity(intent);
        }
    }
}

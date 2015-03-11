package alonedroid.com.calmemo.scene.calendar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.res.StringRes;

import java.util.Calendar;
import java.util.Date;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.CmUtility;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.view.CmDateView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

@EFragment
public class CmCalendarFragment extends Fragment {

    @StringRes(R.string.arg_display_date)
    static String ARG_DISPLAY_DATE;

    private String mDisplayDate;

    private String[][] mMonth = new String[6][7];

    private OnFragmentInteractionListener mListener;

    public static CmCalendarFragment newInstance(String display_date) {
        Bundle args = new Bundle();
        args.putString(ARG_DISPLAY_DATE, display_date);

        CmCalendarFragment fragment = new CmCalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 曜日を配列に格納
        String[] week_str = getResources().getStringArray(R.array.date_strings);
        for (int i = 0; i < week_str.length; i++) {
            this.mMonth[0][i] = week_str[i];
        }

        // 日付を配列に格納
        final Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DATE, 1);
        for (int i = 0; i < 31; i++) {
            int week_no = cal.get(Calendar.WEEK_OF_MONTH);
            int date_no = cal.get(Calendar.DAY_OF_WEEK) - 1;
            this.mMonth[week_no][date_no] = String.valueOf(cal.get(Calendar.DATE));
            cal.add(Calendar.DATE, 1);
        }

        if (getArguments() != null) {
            mDisplayDate = getArguments().getString(ARG_DISPLAY_DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_mc_calendar, container, false);
        ((TextView) ll.findViewById(R.id.cm_calendar_ym)).setText(this.mDisplayDate);

        final int week_num = 7;
        final int row_max = 6;
        final int right_margin = 1;
        for (int i = 0; i < row_max; i++) {
            LinearLayout ll_week = (LinearLayout) ll.findViewById(getResources().getIdentifier("cm_calendar_" + i, "id", getActivity().getPackageName()));

            int left_width = CmApplication.mDisplayWidth;
            for (int j = 0; j < week_num; j++) {
                int width = left_width / (week_num - j);
                left_width -= width;
                CmDateView cv = new CmDateView(getActivity());
                cv.setDate(this.mMonth[i][j]);
                cv.setDateColor(j % 7);
                cv.setDateImage(getPhoto("201503" + String.format("%2s", this.mMonth[i][j]).replace(" ", "0")));
                cv.setLayoutParams(new LinearLayout.LayoutParams(width, FrameLayout.LayoutParams.MATCH_PARENT));
                if (j + 1 < week_num) {
                    cv.setPadding(0, 0, right_margin, 0);
                }
                ll_week.addView(cv);
            }
        }

        return ll;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
//            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public Bitmap getPhoto(String date) {
        Realm realm = Realm.getInstance(getActivity(), getString(R.string.realm_instance));

        RealmQuery<CmPhoto> query = realm.where(CmPhoto.class);
        query.equalTo(CmPhoto.DATE, date);
        RealmResults<CmPhoto> resultAll = query.findAll();
        RealmResults<CmPhoto> result =
                realm.where(CmPhoto.class)
                        .equalTo(CmPhoto.DATE, date)
//                        .or()
//                        .equalTo("name", "Chip")
                        .findAll();

//        RealmResults<CmPhoto> sortedAscending  = result.sort("age");
//
//        RealmResults<CmPhoto> sortedDescending =
//                result.sort("age", RealmResults.SORT_ORDER_DECENDING);

        if (1 <= result.size()) {
            return CmUtility.decodeBitmapString(result.get(0).getPhoto());
        } else {
            return null;
        }

    }
}

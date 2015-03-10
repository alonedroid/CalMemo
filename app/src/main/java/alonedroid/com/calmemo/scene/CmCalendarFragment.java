package alonedroid.com.calmemo.scene;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Calendar;

import alonedroid.com.calmemo.CmUtility;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.view.CmDateView;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CmCalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String[][] mMonth = new String[5][7];
    private int mDisplayWidth;
    private int mDisplayHeight;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment McCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CmCalendarFragment newInstance(String param1, String param2) {
        CmCalendarFragment fragment = new CmCalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CmCalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面サイズ取得
        WindowManager wm = (WindowManager) getActivity().getSystemService(Activity.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        this.mDisplayWidth = size.x;
        this.mDisplayHeight = size.y;

        // 日付を配列に格納
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        for (int i = 0; i < 31; i++) {
            int week_no = cal.get(Calendar.WEEK_OF_MONTH) - 1;
            int date_no = cal.get(Calendar.DAY_OF_WEEK) - 1;
            this.mMonth[week_no][date_no] = String.valueOf(cal.get(Calendar.DATE));
            cal.add(Calendar.DATE, 1);
        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.fragment_mc_calendar, container, false);

        final int week_num = 7;
        final int row_max = 6;
        final int right_margin = 1;
        for (int i = 1; i < row_max; i++) {
            LinearLayout ll_week = (LinearLayout) ll.findViewById(getResources().getIdentifier("cm_calendar_" + i, "id", getActivity().getPackageName()));

            int left_width = this.mDisplayWidth;
            for (int j = 0; j < week_num; j++) {
                int width = left_width / (week_num - j);
                left_width -= width;
                CmDateView cv = new CmDateView(getActivity());
                cv.setDate(this.mMonth[i - 1][j]);
                cv.setDateImage(getPhoto("201503"+String.format("%2s", this.mMonth[i - 1][j]).replace(" ", "0")));
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

        if(1<=result.size()){
            return CmUtility.decodeBitmapString(result.get(0).getPhoto());
        } else {
            return null;
        }

    }
}

package alonedroid.com.calmemo.scene.cover;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import alonedroid.com.calmemo.R;
import hugo.weaving.DebugLog;

@EFragment(R.layout.fragment_mc_cover)
public class CmCoverFragment extends Fragment {

    private static final String ARG_DISPLAY_X = "argDisplayX";

    private static final String ARG_DISPLAY_Y = "argDisplayY";

    @ViewById
    RelativeLayout cm_cover_area;

    @DebugLog
    public static CmCoverFragment newInstance(int display_x, int display_y) {
        Bundle args = new Bundle();
        args.putInt(ARG_DISPLAY_X, display_x);
        args.putInt(ARG_DISPLAY_Y, display_y);

        CmCoverFragment fragment = new CmCoverFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    @AfterViews
    void onAfterViews(){
        this.cm_cover_area.animate().scaleX(200).scaleY(200).setDuration(3000);

    }
}

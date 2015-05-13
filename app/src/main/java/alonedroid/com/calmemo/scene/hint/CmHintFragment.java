package alonedroid.com.calmemo.scene.hint;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.ScrollView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import alonedroid.com.calmemo.R;

@EFragment(R.layout.fragment_cm_hint)
public class CmHintFragment extends Fragment {

    @ViewById
    ScrollView cmHintMainView;

    @ViewById
    AdView adView;

    public static CmHintFragment newInstance() {
        CmHintFragment_.FragmentBuilder_ builder_ = CmHintFragment_.builder();
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        AdRequest adRequest = new AdRequest.Builder().build();
        this.adView.loadAd(adRequest);
    }

    @Click
    void cmHintBackView(View view) {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }
}
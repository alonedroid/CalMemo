package alonedroid.com.calmemo.scene.hint;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.ViewFactory;
import alonedroid.com.calmemo.jackson.JacksonUtility;
import alonedroid.com.calmemo.jackson.ManualObject;

@EFragment(R.layout.fragment_cm_hint)
public class CmHintFragment extends Fragment {

    @Bean
    ViewFactory factory;

    @ViewById
    LinearLayout cmHintMainView;

    public static CmHintFragment newInstance() {
        CmHintFragment_.FragmentBuilder_ builder_ = CmHintFragment_.builder();
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        createManual();
    }

    private void createManual() {
        ManualObject manualObj = (ManualObject) JacksonUtility.getInstance(ManualObject.class);
        for (ManualObject.Manual manual : manualObj.manual) {
            this.cmHintMainView.addView(this.factory.newCmExpandView(manual.title, manual.description));
        }
    }

    @Click
    void cmHintBackView(View view) {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }
}
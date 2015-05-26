package alonedroid.com.calmemo.scene.hint;

import android.app.Activity;
import android.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.io.IOException;
import java.util.List;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.ViewFactory;
import alonedroid.com.calmemo.jackson.JacksonUtility;
import alonedroid.com.calmemo.jackson.ManualObject;
import alonedroid.com.calmemo.utility.AssetsUtility;

@EFragment(R.layout.fragment_cm_hint)
public class CmHintFragment extends Fragment {

    @Bean
    ViewFactory factory;

    @ViewById
    LinearLayout cmHintMainView;

    @StringRes
    String hintCopyright;

    public static CmHintFragment newInstance() {
        CmHintFragment_.FragmentBuilder_ builder_ = CmHintFragment_.builder();
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        createManual();
        addCopyrights();
    }

    private void createManual() {
        ManualObject manualObj = (ManualObject) JacksonUtility.getInstance(ManualObject.class);
        for (ManualObject.Manual manual : manualObj.manual) {
            this.cmHintMainView.addView(this.factory.newCmExpandView(manual.title, manual.description));
        }
    }

    private void addCopyrights() {
        String copyright = getCopyrightString();
        if (TextUtils.isEmpty(copyright)) return;

        this.cmHintMainView.addView(this.factory.newCmExpandView(this.hintCopyright, copyright));
    }

    private String getCopyrightString() {
        String text = "";
        try {
            List<String> list = AssetsUtility.getCopyrightsFileList(getActivity());
            for (String fileName : list) {
                String copyright = AssetsUtility.readFile(getActivity(), fileName);
                text += copyright;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    @Click
    void cmHintBackView(View view) {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }
}
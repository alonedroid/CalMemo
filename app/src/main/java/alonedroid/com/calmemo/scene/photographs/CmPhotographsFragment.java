package alonedroid.com.calmemo.scene.photographs;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.fragment.CmDialogFragment;
import alonedroid.com.calmemo.utility.BitmapUtility;

@EFragment(R.layout.fragment_cm_photographs)
public class CmPhotographsFragment extends Fragment {

    @FragmentArg
    String argDisplayImageKey;

    @FragmentArg
    String argDisplayImageString;

    @ViewById
    ImageView cmPhotographsImage;

    public static CmPhotographsFragment newInstance(String key, String image) {
        CmPhotographsFragment_.FragmentBuilder_ builder_ = CmPhotographsFragment_.builder();
        builder_.argDisplayImageKey(key);
        builder_.argDisplayImageString(image);
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        this.cmPhotographsImage.setImageBitmap(BitmapUtility.decodeBitmapString(this.argDisplayImageString));
    }

    @Click
    void cmPhotographsBackView(View view) {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    @Click
    void cmPhotographsDeleteView(View view) {
        CmDialogFragment.showFragment(getFragmentManager(), this::responsePositive, this::responseNegative);
    }

    private void responsePositive(View view) {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private void responseNegative(View view) {

    }
}

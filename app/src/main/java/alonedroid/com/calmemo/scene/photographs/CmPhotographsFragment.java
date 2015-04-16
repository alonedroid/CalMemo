package alonedroid.com.calmemo.scene.photographs;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.utility.BitmapUtility;
import hugo.weaving.DebugLog;

@EFragment(R.layout.fragment_cm_photographs)
public class CmPhotographsFragment extends Fragment {

    @FragmentArg
    String argDisplayImageKey;

    @FragmentArg
    String argDisplayImageString;

    @ViewById
    ImageView cmPhotographsImage;

    @DebugLog
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
}

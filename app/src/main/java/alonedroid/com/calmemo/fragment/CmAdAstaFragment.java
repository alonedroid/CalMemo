package alonedroid.com.calmemo.fragment;

import android.app.Fragment;
import android.view.View;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

import alonedroid.com.calmemo.R;
import jp.maru.mrd.IconCell;
import jp.maru.mrd.IconLoader;

@EFragment(R.layout.fragment_cm_ad_asta)
public class CmAdAstaFragment extends Fragment {

    @ViewsById({R.id.cm_asta_1, R.id.cm_asta_2, R.id.cm_asta_3, R.id.cm_asta_4, R.id.cm_asta_5, R.id.cm_asta_6})
    List<View> adViews;

    IconLoader<Integer> myIconLoader;

    @AfterInject
    void init() {
        if (myIconLoader == null) {
            myIconLoader = new IconLoader<Integer>("__MEDIA_CODE__", this);
            myIconLoader.setRefreshInterval(30);
            for (View v : adViews) {
                ((IconCell) v).addToIconLoader(myIconLoader);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myIconLoader.startLoading();
    }

    @Override
    public void onPause() {
        myIconLoader.stopLoading();
        super.onPause();
    }
}
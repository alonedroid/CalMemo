package alonedroid.com.calmemo.fragment;

import android.app.Fragment;
import android.view.View;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

import alonedroid.com.calmemo.R;
import jp.maru.mrd.IconCell;
import jp.maru.mrd.IconLoader;

@EFragment(R.layout.fragment_cm_ad_asta)
public class CmAdAstaFragment extends Fragment {

    @ViewsById({R.id.cm_asta_1, R.id.cm_asta_2, R.id.cm_asta_3, R.id.cm_asta_4})
    List<View> adViews;

    IconLoader<Integer> myIconLoader;

    @AfterViews
    void initViews() {
        if (myIconLoader == null) {
            myIconLoader = new IconLoader<Integer>("ast02327lpxd71v95jvg", getActivity());
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
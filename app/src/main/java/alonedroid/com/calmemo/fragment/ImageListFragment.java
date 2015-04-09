package alonedroid.com.calmemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.ViewFactory;
import alonedroid.com.calmemo.realm.CmPhoto;

@EFragment(R.layout.fragment_image_list)
public class ImageListFragment extends Fragment {

    private static final String ARG_IMAGE_LIST = "argImageList";

    private static final int DIVIDE_WIDTH_PART = 4;

    @Bean
    ViewFactory factory;

    @FragmentArg
    CmPhoto[] argImageList;

    @ViewById
    LinearLayout fragmentImageList;

    public static ImageListFragment newInstance(CmPhoto[] imageList) {
        ImageListFragment fragment = new ImageListFragment_();
        Bundle args = new Bundle();
        args.putSerializable(ARG_IMAGE_LIST, imageList);
        fragment.setArguments(args);
        return fragment;
    }

    @AfterViews
    void onAfterViews() {
        generateImageList();
    }

    private void generateImageList() {
        int divideDisplayWidth = CmApplication.divideDisplayWidth(DIVIDE_WIDTH_PART);

        ImageView image;
        LinearLayout relative = factory.newLinearLayout(ViewGroup.LayoutParams.WRAP_CONTENT, divideDisplayWidth);
        for (int i = 0; i < this.argImageList.length; i++) {
            if (i % DIVIDE_WIDTH_PART == 0) {
                relative = factory.newLinearLayout(ViewGroup.LayoutParams.WRAP_CONTENT, divideDisplayWidth);
                this.fragmentImageList.addView(relative);
            }
            image = factory.newImageView(argImageList[i].getCm_photo(), divideDisplayWidth, divideDisplayWidth);
            relative.addView(image);
        }
    }
}

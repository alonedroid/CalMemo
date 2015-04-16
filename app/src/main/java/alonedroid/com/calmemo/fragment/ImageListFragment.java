package alonedroid.com.calmemo.fragment;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.ViewFactory;
import alonedroid.com.calmemo.realm.CmPhoto;

@EFragment(R.layout.fragment_image_list)
public class ImageListFragment extends Fragment {

    private static final int DIVIDE_WIDTH_PART = 4;

    @Bean
    ViewFactory factory;

    @FragmentArg
    CmPhoto[] argImageList;

    @ViewById
    LinearLayout fragmentImageList;

    OnImageClickedListener listener;

    public static ImageListFragment newInstance(CmPhoto[] imageList) {
        ImageListFragment_.FragmentBuilder_ builder_ = ImageListFragment_.builder();
        builder_.argImageList(imageList);
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        generateImageList();
    }

    private void generateImageList() {
        int divideDisplayWidth = CmApplication.divideDisplayWidth(DIVIDE_WIDTH_PART);

        ImageView image;
        LinearLayout layout = this.factory.newLinearLayout(ViewGroup.LayoutParams.WRAP_CONTENT, divideDisplayWidth);
        for (int i = 0; i < this.argImageList.length; i++) {
            if (i % DIVIDE_WIDTH_PART == 0) {
                layout = this.factory.newLinearLayout(ViewGroup.LayoutParams.WRAP_CONTENT, divideDisplayWidth);
                this.fragmentImageList.addView(layout);
            }
            final int index = i;
            image = this.factory.newImageView(this.argImageList[i].getCmPhoto(), divideDisplayWidth, divideDisplayWidth);
            image.setOnClickListener(view -> this.listener.onClick(this.argImageList[index]));
            layout.addView(image);
        }
    }

    public void setClickedListener(OnImageClickedListener listener) {
        this.listener = listener;
    }

    public interface OnImageClickedListener {
        public void onClick(CmPhoto bean);
    }
}

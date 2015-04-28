package alonedroid.com.calmemo.fragment;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import alonedroid.com.calmemo.ViewFactory;
import alonedroid.com.calmemo.realm.CmPhoto;
import alonedroid.com.calmemo.view.CmImageView;

@EFragment(R.layout.fragment_image_list)
public class ImageListFragment extends Fragment {

    private static final int DIVIDE_WIDTH_PART = 4;

    @ColorRes
    int thinBlack;

    @Bean
    ViewFactory factory;

    @FragmentArg
    CmPhoto[] argImageList;

    @ViewById
    LinearLayout fragmentImageList;

    private int leftPhotoNum;

    OnImageClickedListener listener;

    public static ImageListFragment newInstance(CmPhoto[] imageList) {
        ImageListFragment_.FragmentBuilder_ builder_ = ImageListFragment_.builder();
        builder_.argImageList(imageList);
        return builder_.build();
    }

    @AfterViews
    void onAfterViews() {
        generateImageList();
        this.leftPhotoNum = this.argImageList.length;
    }

    private void generateImageList() {
        final int divideDisplayWidth = CmApplication.divideDisplayWidth(DIVIDE_WIDTH_PART);

        CmImageView image;
        LinearLayout layout = this.factory.newLinearLayout(ViewGroup.LayoutParams.WRAP_CONTENT, divideDisplayWidth);
        for (int i = 0; i < this.argImageList.length; i++) {
            if (i % DIVIDE_WIDTH_PART == 0) {
                layout = this.factory.newLinearLayout(ViewGroup.LayoutParams.WRAP_CONTENT, divideDisplayWidth);
                this.fragmentImageList.addView(layout);
            }
            final int index = i;
            image = this.factory.newImageView(this.argImageList[i].getCmPhoto(), divideDisplayWidth, divideDisplayWidth);
            image.setOnClickListener(view -> this.listener.onClick(index, this.argImageList[index]));
            layout.addView(image);
        }
    }

    public void setClickedListener(OnImageClickedListener listener) {
        this.listener = listener;
    }

    public void markDelete(int index) {
        int row = index / DIVIDE_WIDTH_PART;
        int col = index % DIVIDE_WIDTH_PART;
        ((CmImageView) ((LinearLayout) this.fragmentImageList.getChildAt(row)).getChildAt(col)).showDeleteMark();
        this.leftPhotoNum--;
    }

    public boolean isAllDeleted() {
        return this.leftPhotoNum == 0;
    }

    public interface OnImageClickedListener {
        public void onClick(int index, CmPhoto bean);
    }
}

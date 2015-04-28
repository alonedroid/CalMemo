package alonedroid.com.calmemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import alonedroid.com.calmemo.R;


public class CmImageView extends FrameLayout {

    private FrameLayout rootView;

    private ImageView imageView;

    private FrameLayout frameView;

    public CmImageView(Context context) {
        this(context, null);
    }

    public CmImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CmImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_cm_image, this);

        this.rootView = (FrameLayout) v.findViewById(R.id.view_image_root);
        this.imageView = (ImageView) v.findViewById(R.id.view_image_photo);
        this.frameView = (FrameLayout) v.findViewById(R.id.view_image_frame);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.imageView.setImageBitmap(bitmap);
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        this.imageView.setScaleType(scaleType);
    }

    public void showDeleteMark() {
        this.frameView.setVisibility(View.VISIBLE);
    }
}

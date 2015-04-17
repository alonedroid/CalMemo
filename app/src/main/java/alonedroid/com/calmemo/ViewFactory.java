package alonedroid.com.calmemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import alonedroid.com.calmemo.utility.BitmapUtility;
import alonedroid.com.calmemo.view.CmDateView;

@EBean
public class ViewFactory {

    @RootContext
    Context context;

    public RelativeLayout newRelativeLayout(int width, int height) {
        RelativeLayout relative = new RelativeLayout(context);
        setLayoutParams(relative, width, height);

        return relative;
    }

    public LinearLayout newLinearLayout(int width, int height) {
        LinearLayout linear = new LinearLayout(context);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(linear, width, height);

        return linear;
    }

    public ImageView newImageView(String src, int width, int height) {
        ImageView image = new ImageView(this.context);
        setLayoutParams(image, width, height);
        image.setImageBitmap(BitmapUtility.decodeBitmapString(src));
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return image;
    }

    public CmDateView newCmDateView(String date, int color, Bitmap bitmap, int width, int height, int frame) {
        CmDateView view = new CmDateView(this.context);
        setLayoutParams(view, width, height);
        view.setDate(date);
        view.setDateColor(color);
        view.setDateImage(bitmap);
        view.setPadding(0, frame, frame, 0);

        return view;
    }

    public void setLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        view.setLayoutParams(layoutParams);
    }
}

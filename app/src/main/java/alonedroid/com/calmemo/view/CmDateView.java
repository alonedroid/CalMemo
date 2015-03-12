package alonedroid.com.calmemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import alonedroid.com.calmemo.R;


public class CmDateView extends FrameLayout {
    private ImageView mDateImage;
    private TextView mDateText;

    public CmDateView(Context context) {
        this(context, null);
    }

    public CmDateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CmDateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_cm_date, this);

        this.mDateImage = (ImageView) v.findViewById(R.id.cm_date_image);
        this.mDateText = (TextView) v.findViewById(R.id.cm_date_text);
    }

    public void setDate(String date) {
        if (TextUtils.isEmpty(date)) {
            mDateText.setText("");
        } else {
            mDateText.setText(date);
        }
    }

    public void setDateColor(int color){
        mDateText.setTextColor(color);
    }

    public void setDateImage(Bitmap image) {
        if (image != null) {
            mDateText.setTextColor(Color.WHITE);
        }

        mDateImage.setImageBitmap(image);
    }
}

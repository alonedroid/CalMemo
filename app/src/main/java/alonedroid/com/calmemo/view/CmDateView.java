package alonedroid.com.calmemo.view;

import android.content.Context;
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

    private ImageView dateImageView;

    private TextView dateTextView;

    private boolean hasImage;

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

        this.dateImageView = (ImageView) v.findViewById(R.id.cm_date_image);
        this.dateTextView = (TextView) v.findViewById(R.id.cm_date_text);
    }

    public String getDate() {
        return this.dateTextView.getText().toString();
    }

    public void setDate(String date) {
        if (TextUtils.isEmpty(date)) {
            this.dateTextView.setText("");
        } else {
            this.dateTextView.setText(date);
        }
    }

    public void setDateColor(int color) {
        this.dateTextView.setTextColor(color);
    }

    public void setDateImage(Bitmap image) {
        this.hasImage = (image != null);

        if (this.hasImage) {
            this.dateTextView.setTextColor(Color.WHITE);
        }

        this.dateImageView.setImageBitmap(image);
    }

    public void reset() {
        this.hasImage = false;
        this.dateTextView.setTextColor(getResources().getColor(R.color.cm_text_color));
        this.dateImageView.setImageBitmap(null);
    }

    public boolean hasImage() {
        return this.hasImage;
    }
}

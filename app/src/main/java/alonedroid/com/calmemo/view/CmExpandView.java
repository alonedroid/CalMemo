package alonedroid.com.calmemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import alonedroid.com.calmemo.R;

public class CmExpandView extends FrameLayout {

    private TextView titleView;

    private TextView descriptionView;

    public CmExpandView(Context context) {
        this(context, null);
    }

    public CmExpandView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CmExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inject(context);
    }

    private void inject(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_cm_expand, this);

        this.titleView = (TextView) v.findViewById(R.id.cm_expand_title);
        this.titleView.setOnClickListener(titleView -> expandDescription());
        this.descriptionView = (TextView) v.findViewById(R.id.cm_expand_description);
    }

    private void expandDescription() {
        this.descriptionView.setVisibility((this.descriptionView.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
    }

    public void setTitle(String title) {
        this.titleView.setText(title);
    }

    public void setDescription(String description) {
        this.descriptionView.setText(description);
    }
}

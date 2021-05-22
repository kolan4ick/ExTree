package com.example.extree;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ScrollView;

public class ScrollViewOverBottomMenu extends ScrollView {
    private Context mContext;
    private int heightOfMenu;

    public void setHeightOfMenu(int heightOfMenu) {
        this.heightOfMenu = heightOfMenu;
    }

    public ScrollViewOverBottomMenu(Context context) {
        super(context);
        init(context);
    }

    public ScrollViewOverBottomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollViewOverBottomMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ScrollViewOverBottomMenu(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            // display the maximum height of half the height of the screen content
            Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
            DisplayMetrics d = new DisplayMetrics();
            display.getMetrics(d);
            // Here is the key, set the controls height can not exceed half the screen height (d.heightPixels / 2) (in this height they need replacing)
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(d.heightPixels, MeasureSpec.AT_MOST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // recalculate the high controls, wide
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}

package com.mingle.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mingle.shapeloading.R;

/**
 * Created by linyueyang on 16/12/27.
 */

public class FrameLoadingView extends FrameLayout {

    ImageView imageView;
    AnimationDrawable animationDrawable;
    Context context;

    public FrameLoadingView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FrameLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FrameLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FrameLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private void init() {

        View view = LayoutInflater.from(context).inflate(R.layout.load_animation_list, this, true);

        imageView = (ImageView) view.findViewById(R.id.iv_animation);

        imageView.setImageResource(R.drawable.animation_list);
        //给动画资源赋值
        animationDrawable = (AnimationDrawable) imageView.getDrawable();

        animationDrawable.start();
    }
}

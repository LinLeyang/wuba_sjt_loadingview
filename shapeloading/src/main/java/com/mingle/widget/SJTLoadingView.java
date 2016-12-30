package com.mingle.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mingle.shapeloading.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


/**
 * Created by linyueyang on 16/12/28.
 */
public class SJTLoadingView extends FrameLayout {

    private static final int ANIMATION_DURATION = 1000;

    private static float xDistance = 20;
    private static float yDistance = 30;

    private SJTShapeLoadingView mSJTShapeLoadingView;
    private SJTShapeLoadingView mSJTShapeLoadingView2;
    private AnimatorSet mUpAnimatorSet;
    private AnimatorSet mDownAnimatorSet;

    private boolean run;//动画启动标识

    public SJTLoadingView(Context context) {
        super(context);
    }

    public SJTLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs);

    }

    public SJTLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SJTLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WUBALoadingView);
        typedArray.recycle();
        run = true;
    }

    public int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onFinishInflate() {
        Log.e("LoadingView", "onFinishInflate");
        super.onFinishInflate();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.sjt_load_view, null);

        xDistance = dip2px(28.5f);
        yDistance = dip2px(38.5f);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        mSJTShapeLoadingView = (SJTShapeLoadingView) view.findViewById(R.id.shapeLoadingView);
        mSJTShapeLoadingView2 = (SJTShapeLoadingView) view.findViewById(R.id.shapeLoadingView2);
        mSJTShapeLoadingView.setColor(R.color.red);
        mSJTShapeLoadingView2.setColor(R.color.pink);

        addView(view, layoutParams);
        startLoading(0);
    }


    private AnimatorSet mAnimatorSet = null;

    private Runnable mFreeFallRunnable = new Runnable() {
        @Override
        public void run() {
            startAnimator();
        }
    };

    private void startLoading(long delay) {
        Log.e("LoadingView", "startLoading");
        if (mDownAnimatorSet != null && mDownAnimatorSet.isRunning()) {
            return;
        }
        this.removeCallbacks(mFreeFallRunnable);
        if (delay > 0) {
            this.postDelayed(mFreeFallRunnable, delay);
        } else {
            this.post(mFreeFallRunnable);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        Log.e("LoadingView", "onDetachedFromWindow");
        super.onDetachedFromWindow();
        stopLoading();
    }

    private void stopLoading() {

        run = false;

        Log.e("LoadingView", "stopLoading");
        if (mAnimatorSet != null) {
            //if (mAnimatorSet.isRunning()) {
            mAnimatorSet.cancel();
            //}
            mAnimatorSet.removeAllListeners();
            for (Animator animator : mAnimatorSet.getChildAnimations()) {
                animator.removeAllListeners();
                animator.cancel();
            }
        }
        if (mUpAnimatorSet != null) {
            //if (mUpAnimatorSet.isRunning()) {
            mUpAnimatorSet.cancel();
            mUpAnimatorSet.end();
            //}
            mUpAnimatorSet.removeAllListeners();
            for (Animator animator : mUpAnimatorSet.getChildAnimations()) {
                animator.removeAllListeners();
                animator.cancel();
            }
        }
        if (mDownAnimatorSet != null) {
            //if (mDownAnimatorSet.isRunning()) {
            mDownAnimatorSet.cancel();
            mDownAnimatorSet.end();
            //}
            mDownAnimatorSet.removeAllListeners();
            for (Animator animator : mDownAnimatorSet.getChildAnimations()) {
                animator.removeAllListeners();
                animator.cancel();
            }
        }
        this.removeCallbacks(mFreeFallRunnable);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE) {
            startLoading(200);
        } else {
            stopLoading();
        }
    }


    public void startAnimator() {

        if (null == mAnimatorSet) {
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "translationY", 0, yDistance);
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "translationX", 0, xDistance);
            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "scaleX", 1, 0.1f);
            ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "scaleY", 1, 0.1f);


            objectAnimator1.setDuration(1);
            objectAnimator2.setDuration(1);
            objectAnimator3.setDuration(1);
            objectAnimator4.setDuration(1);

            mAnimatorSet = new AnimatorSet();
            mAnimatorSet.playTogether(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4);

            mAnimatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (run)
                        freeFall();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

        }
        mAnimatorSet.start();

    }

    /**
     * 上抛
     */
    public void upThrow() {
        Log.e("LoadingView", "upThrow");
        mSJTShapeLoadingView.bringToFront();


        if (null == mUpAnimatorSet) {
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "translationY", yDistance, 0);
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "translationX", xDistance, 0);
            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "scaleX", 0.1f, 1);
            ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "scaleY", 0.1f, 1);

            ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "translationY", 0, yDistance);
            ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "translationX", 0, xDistance);
            ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "scaleX", 1, 0.1f);
            ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "scaleY", 1, 0.1f);

            objectAnimator1.setDuration(ANIMATION_DURATION);
            objectAnimator2.setDuration(ANIMATION_DURATION);
            objectAnimator3.setDuration(ANIMATION_DURATION);
            objectAnimator4.setDuration(ANIMATION_DURATION);
            objectAnimator5.setDuration(ANIMATION_DURATION);
            objectAnimator6.setDuration(ANIMATION_DURATION);
            objectAnimator7.setDuration(ANIMATION_DURATION);
            objectAnimator8.setDuration(ANIMATION_DURATION);

            mUpAnimatorSet = new AnimatorSet();
//        mUpAnimatorSet.setDuration(ANIMATION_DURATION);
//        mUpAnimatorSet.setInterpolator(new DecelerateInterpolator(factor));
            mUpAnimatorSet.playTogether(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8);


            mUpAnimatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (run)
                        freeFall();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mUpAnimatorSet.start();


    }

    /**
     * 下落
     */
    public void freeFall() {
        Log.e("LoadingView", "freeFall");
        mSJTShapeLoadingView2.bringToFront();
        if (null == mDownAnimatorSet) {

            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "translationY", 0, yDistance);
            ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "translationX", 0, xDistance);
            ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "scaleX", 1, 0.1f);
            ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(mSJTShapeLoadingView, "scaleY", 1, 0.1f);

            //ObjectAnimator scaleIndication = ObjectAnimator.ofFloat(mIndicationIm, "scaleX", 1, 0.2f);

            ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "translationY", yDistance, 0);
            ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "translationX", xDistance, 0);
            ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "scaleX", 0.1f, 1);
            ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(mSJTShapeLoadingView2, "scaleY", 0.1f, 1);


            objectAnimator1.setDuration(ANIMATION_DURATION);
            objectAnimator2.setDuration(ANIMATION_DURATION);
            objectAnimator3.setDuration(ANIMATION_DURATION);
            objectAnimator4.setDuration(ANIMATION_DURATION);
            objectAnimator5.setDuration(ANIMATION_DURATION);
            objectAnimator6.setDuration(ANIMATION_DURATION);
            objectAnimator7.setDuration(ANIMATION_DURATION);
            objectAnimator8.setDuration(ANIMATION_DURATION);

            mDownAnimatorSet = new AnimatorSet();
            mDownAnimatorSet.playTogether(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4, objectAnimator5, objectAnimator6, objectAnimator7, objectAnimator8);
            mDownAnimatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (run) {
                        upThrow();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        mDownAnimatorSet.start();


    }

}

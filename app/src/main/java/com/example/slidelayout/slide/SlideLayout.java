package com.example.slidelayout.slide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.example.slidelayout.R;

public class SlideLayout extends FrameLayout {

    private Activity mActivity;
    private Scroller mScroller;
    // 页面边缘的阴影图
    private Drawable mLeftShadow;
    //系统可以辨别的最小滑动距离
    private int minTouchSlop;
    //松手之后滑动时间
    private int smoothscrollTime = 300;
    //结束activity的屏占比
    private float mSlideFinishRadio = 0.382f;
    //按下的位置
    private float mDownX, mDownY;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        //获取系统能捕获的最小滑动距离
        minTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mLeftShadow = getResources().getDrawable(R.drawable.left_shadow);
    }

    /**
     * 绑定Activity
     */
    public void attachActivity(Activity activity) {
        mActivity = activity;
        ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
        View child = decorView.getChildAt(0);
        child.setBackgroundResource(android.R.color.white);
        decorView.removeView(child);
        addView(child);
        decorView.addView(this);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean interceptd = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interceptd = false;
                mDownX = event.getX();
                mDownY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                //计算移动距离 判定是否滑动
                float dx = event.getX() - mDownX;
                float dy = event.getY() - mDownY;
                boolean canScrollHorizontally = canScrollHorizontally(-1, this);
                Log.e("TAG", "是否可以滚动："+canScrollHorizontally);

                if (dx > minTouchSlop && dx - Math.abs(dy) > minTouchSlop && !canScrollHorizontally ) {
                    interceptd = true;
                } else {
                    interceptd = false;
                }

                break;

            case MotionEvent.ACTION_UP:
                interceptd = false;
                break;
        }

        return interceptd;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("TAG", "事件分发："+event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - mDownX;
                if (getScrollX() - dx >= 0) {
                    scrollTo(0, 0);
                } else {
                    scrollBy((int) -dx, 0);
                }

                mDownX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                // 根据手指释放时的位置决定回弹还是关闭
                int scrollX = getScrollX();
                if (-scrollX < getWidth() * mSlideFinishRadio) {
                    smoothScrollX(scrollX, -scrollX, smoothscrollTime, mScroller);
                } else {
                    smoothScrollX(scrollX, -scrollX - getWidth(), smoothscrollTime, mScroller);
                }
                break;
        }
        return true;
    }


    /**
     * 平滑的滚动到某个位置
     *
     * @param startX    开始位置
     * @param endX      结束位置
     * @param duration  时间
     * @param mScroller
     */
    private void smoothScrollX(int startX, int endX, int duration, Scroller mScroller) {
        mScroller.startScroll(startX, 0, endX, 0, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        } else if (-getScrollX() >= getWidth()) {
            mActivity.finish();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShadow(canvas);
    }

    /**
     * 绘制边缘的阴影
     */
    private void drawShadow(Canvas canvas) {
        mLeftShadow.setBounds(0, 0, mLeftShadow.getIntrinsicWidth(), getHeight());
        canvas.save();
        canvas.translate(-mLeftShadow.getIntrinsicWidth(), 0);
        mLeftShadow.draw(canvas);
        canvas.restore();
    }


    /**
     * 是否左右可以滚动
     *
     * @param direction
     * @param view
     */
    private boolean canScrollHorizontally(int direction, View view) {
        if (view.canScrollHorizontally(direction)) {
            return true;
        } else {
            if (view instanceof ViewGroup) {
                ViewGroup viewParent = (ViewGroup) view;
                int childCount = viewParent.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View chideView = viewParent.getChildAt(i);
                    boolean childCanScroll = canScrollHorizontally(direction, chideView);
                    if (childCanScroll) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

}
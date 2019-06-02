package com.example.slidelayout.slide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.slidelayout.R;
import com.example.slidelayout.helper.WindowUtil;

import java.lang.reflect.Field;

/**
 * @author gexinyu
 * 利用ViewDragHelper实现滑动
 */
public class DragLayout extends FrameLayout {

    private View mRootView;
    private Activity mActivity;
    private ViewDragHelper mViewDragHelper;

    //距离边缘最远距离 
    private float mMaxSlideWidth;
    //屏幕的宽
    private int mScreenWidth;
    //这个是判定比例 结束当前页面
    private float SCREENT_FINISH_RADIO = 0.382f;
    //这个是边缘的背景
    private Drawable mEdgeShadow;
    //这是划开之后背景色的初始值 随着距离改变改变透明度显示更好的效果
    private static final int DEFAULT_BACKGROUND_COLOR = 0x99000000;
    //全透明的参数
    private static final int FULL_ALPHA = 255;
    //这是当前屏幕移开的比例 相对于整个屏幕
    private float mScrollPercent;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //必须是传入Activity
        mActivity = (Activity) context;
        //构造ViewDragHelper
        mViewDragHelper = ViewDragHelper.create(this, new DragCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
        mEdgeShadow = getResources().getDrawable(R.drawable.left_shadow);
        mScreenWidth = WindowUtil.getScreenWidth(mActivity);
        mMaxSlideWidth = mScreenWidth * SCREENT_FINISH_RADIO;
        //设置默认全屏触摸
        setTouchRange(mScreenWidth);
    }

    /**
     * 设置可以拖拽的触点范围
     *
     * @param touchRange
     */
    private void setTouchRange(int touchRange) {
        Class<?> aClass = mViewDragHelper.getClass();
        Field mDividerHeight = null;
        try {
            mDividerHeight = aClass.getDeclaredField("mEdgeSize");
            mDividerHeight.setAccessible(true);
            mDividerHeight.setInt(mViewDragHelper, touchRange);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 在Activity的DecorView下插入当前ViewGroup,原来的RootView放于当前ViewGroup下
     *
     * @param activity
     */
    public void attachActivity(Activity activity) {
        mActivity = activity;
        ViewGroup mDecorView = (ViewGroup) mActivity.getWindow().getDecorView();
        mRootView = mDecorView.getChildAt(0);
        mRootView.setBackgroundResource(android.R.color.white);
        mDecorView.removeView(mRootView);
        this.addView(mRootView);
        mDecorView.addView(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean canScrollHorizontally = canScrollHorizontally(-1, this);
        if (!canScrollHorizontally) {
            return mViewDragHelper.shouldInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 拖拽的回调
     */
    class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //当前回调，松开手时触发，比较触发条件和当前的滑动距离
            int left = releasedChild.getLeft();
            if (left <= mMaxSlideWidth) {
                //缓慢滑动的方法,小于触发条件，滚回去
                mViewDragHelper.settleCapturedViewAt(0, 0);
            } else {
                //大于触发条件，滚出去...
                mViewDragHelper.settleCapturedViewAt(mScreenWidth, 0);
            }

            //需要手动调用更新界面的方法
            invalidate();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            mScrollPercent = Math.abs((float) left
                    / (mScreenWidth + mEdgeShadow.getIntrinsicWidth()));
            //重绘
            invalidate();

            //当滚动位置到达屏幕最右边，则关掉Activity
            if (changedView == mRootView && left >= mScreenWidth) {
                mActivity.finish();
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return ViewDragHelper.EDGE_LEFT;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //限制左右拖拽的位移
            left = left >= 0 ? left : 0;
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            //上下不能移动，返回0
            return 0;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            //触发边缘时，主动捕捉mRootView
            mViewDragHelper.captureChildView(mRootView, pointerId);
        }
    }

    @Override
    public void computeScroll() {
        //使用settleCapturedViewAt方法是，必须重写computeScroll方法，传入true
        //持续滚动期间，不断刷新ViewGroup
        if (mViewDragHelper.continueSettling(true))
            ViewCompat.postInvalidateOnAnimation(this);

    }


    /**
     * 这里主要是背景以及边缘背景颜色的渐变的重绘
     *
     * @param canvas
     * @param child
     * @param drawingTime
     * @return
     */
    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        final boolean drawContent = child == mRootView;
        boolean ret = super.drawChild(canvas, child, drawingTime);
        boolean isNotIdle = mViewDragHelper.getViewDragState() != ViewDragHelper.STATE_IDLE;
        if (mScrollPercent < 1
                && mScrollPercent > 0
                && drawContent
                && isNotIdle) {
            drawEdgeShadow(canvas, child);
            drawBackGroundGradient(canvas, child);
        }
        return ret;
    }


    /**
     * 设置当前activity边缘的背景色
     *
     * @param canvas
     * @param child
     */
    private void drawEdgeShadow(Canvas canvas, View child) {
        final Rect childRect = new Rect();
        child.getHitRect(childRect);
        mEdgeShadow.setBounds(childRect.left - mEdgeShadow.getIntrinsicWidth(), childRect.top, childRect.left, childRect.bottom);
        mEdgeShadow.setAlpha((int) ((1 - mScrollPercent) * FULL_ALPHA));
        mEdgeShadow.draw(canvas);
    }


    /**
     * 这里是设置划开的后面对面页面的背景色渐变
     *
     * @param canvas
     * @param child
     */
    private void drawBackGroundGradient(Canvas canvas, View child) {
        final int baseAlpha = (DEFAULT_BACKGROUND_COLOR & 0xff000000) >>> 24;
        final int alpha = (int) (baseAlpha * (1 - mScrollPercent));
        final int color = alpha << 24 | (DEFAULT_BACKGROUND_COLOR & 0xffffff);
        canvas.clipRect(0, 0, child.getLeft(), getHeight());
        canvas.drawColor(color);
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
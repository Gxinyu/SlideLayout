package com.example.slidelayout.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.slidelayout.R;

/**
 * @author gexinyu
 */
public class DragViewGroup extends FrameLayout {

    private Context mContext;
    //按下位置
    private float mDownX, mDownY;
    private int mParentWidth = 0;
    private int mParentHeight = 0;
    private int minTouchSlop;//系统可以辨别的最小滑动距离
    private boolean canScrollH, canScrollH2;//水平方向上滚动

    public DragViewGroup(@NonNull Context context) {
        this(context, null);
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        minTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
        LayoutInflater.from(mContext).inflate(R.layout.custom_drag_viewgroup, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup mViewGroup = (ViewGroup) getParent();
        mParentHeight = mViewGroup.getMeasuredHeight();
        mParentWidth = mViewGroup.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean interceptd = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                interceptd = false;
                //测量按下位置
                mDownX = event.getX();
                mDownY = event.getY();

                break;

            case MotionEvent.ACTION_MOVE:
                //计算移动距离 判定是否滑动
                float dx = event.getX() - mDownX;
                float dy = event.getY() - mDownY;
                interceptd = Math.abs(dx) > minTouchSlop || Math.abs(dy) > minTouchSlop;
                break;

            case MotionEvent.ACTION_UP:
                interceptd = false;
                break;
        }

        return interceptd;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                //必须控制在父空间之内

                float dx = event.getX() - mDownX;
                float dy = event.getY() - mDownY;

                float ownX = getX();
                //获取手指按下的距离与控件本身Y轴的距离
                float ownY = getY();
                //理论中X轴拖动的距离
                float endX = ownX + dx;
                //理论中Y轴拖动的距离
                float endY = ownY + dy;
                //X轴可以拖动的最大距离
                float maxX = mParentWidth - getWidth();
                //Y轴可以拖动的最大距离
                float maxY = mParentHeight - getHeight();
                //X轴边界限制
                endX = endX < 0 ? 0 : Math.min(endX, maxX);
                //Y轴边界限制
                endY = endY < 0 ? 0 : Math.min(endY, maxY);
                setX(endX);
                setY(endY);

                break;

            case MotionEvent.ACTION_DOWN:
                float rightX = mParentWidth - getWidth();
                float x = getX();
                canScrollH = x != rightX;
                canScrollH2 = x != 0;

                break;
        }

        return true;
    }


    /**
     * 重写水平方向上是否可以滚动
     *
     * @param direction
     * @return
     */
    @Override
    public boolean canScrollHorizontally(int direction) {
        if (direction < 0) {
            return canScrollH;
        } else {
            return canScrollH2;
        }
    }
}

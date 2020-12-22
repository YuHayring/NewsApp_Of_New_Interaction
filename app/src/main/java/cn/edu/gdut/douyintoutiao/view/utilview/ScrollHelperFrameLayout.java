package cn.edu.gdut.douyintoutiao.view.utilview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import cn.edu.gdut.douyintoutiao.DouYinTouTiaoApplication;

/**
 * @author hayring
 * @date 11/26/20 10:02 PM
 * 上下与左右-滑动冲突辅助布局
 */
public class ScrollHelperFrameLayout extends FrameLayout {
    public ScrollHelperFrameLayout(@NonNull Context context) {
        super(context);
    }

    public ScrollHelperFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollHelperFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollHelperFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 滑动方向找到之前的次数
     */
    int findTimes;

    /**
     * 多少次决定滑动方向
     */
    private static final int FIND_TIMES = 3;

    /**
     * 触发坐标 x
     */
    int startX;

    /**
     * 触发坐标 y
     */
    int startY;
    /**
     * 是否已找到当前滑动状态
     */
    private boolean findCurrent = false;

    boolean vertical;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.d("OnTouch","run");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //重置寻找次数
                findTimes = 0;
                //重置
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                findTimes++;
                break;
            case MotionEvent.ACTION_MOVE:
                if (findTimes > FIND_TIMES) {
                    break;
                }
                if (findTimes < FIND_TIMES) {
                    findTimes++;
                } else {
                    int endX = (int) ev.getX();
                    int endY = (int) ev.getY();
                    int disX = Math.abs(endX - startX);
                    int disY = Math.abs(endY - startY);
                    if (disX > DouYinTouTiaoApplication.SCROLL_TAN * disY) {
                        //横向滑动
                        vertical = false;
                        getParent().requestDisallowInterceptTouchEvent(false);
                        Log.i("滑动方向","横向");
                        return false;
                    } else {
                        //竖向滑动
                        vertical = true;
                        getParent().requestDisallowInterceptTouchEvent(true);
                        Log.i("滑动方向","竖向");
                    }
                    findTimes++;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (vertical) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    vertical = true;
                }

        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!vertical) return false;
        return super.onTouchEvent(e);
    }
}

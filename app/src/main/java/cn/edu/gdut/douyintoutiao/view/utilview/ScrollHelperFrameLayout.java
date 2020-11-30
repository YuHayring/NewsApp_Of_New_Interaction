package cn.edu.gdut.douyintoutiao.view.utilview;

import android.content.Context;
import android.util.AttributeSet;
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



    private int startX, startY;


    /**
     * 是否已找到当前滑动状态
     */
    private boolean findCurrent = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                findCurrent = false;
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (!findCurrent) {
                    int endX = (int) ev.getX();
                    int endY = (int) ev.getY();
                    int disX = Math.abs(endX - startX);
                    int disY = Math.abs(endY - startY);
                    if(disX >  DouYinTouTiaoApplication.SCROLL_TAN * disY){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    findCurrent = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

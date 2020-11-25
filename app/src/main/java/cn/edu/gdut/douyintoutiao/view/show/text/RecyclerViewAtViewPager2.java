package cn.edu.gdut.douyintoutiao.view.show.text;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author hayring
 * 解决 ViewPager2 的左右滑动比 RecyclerView 的上下滑动更灵敏的问题
 * @date 11/24/20 4:35 PM
 */
public class RecyclerViewAtViewPager2 extends RecyclerView {

    public RecyclerViewAtViewPager2(@NonNull Context context) {
        super(context);
    }

    public RecyclerViewAtViewPager2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewAtViewPager2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                    if(disX >  disY){
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
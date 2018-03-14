package com.kang.commentview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;

/**
 * Created by kangren on 2018/3/14.
 */

public class CommentListView extends ListView implements AbsListView.OnScrollListener{

    /**
     * 位移比例
     */
    private final static float OFFSET_RADIO = 1.0f;

    private Context mContext;

    private float mLastY = -1;

    private boolean isDrag;

    /**
     * 外部OnScrollListener
     */
    private OnScrollListener onScrollListener;

    private OnPullDownListener onPullDownListener;

    public CommentListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWithContext(context);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mContext = context;
        setOnScrollListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0 && (isDrag || deltaY > 0)) {
                    updateHeight(deltaY * OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1;
                isDrag = false;
                break;
        }
        return isDrag || super.onTouchEvent(ev);
    }

    private void updateHeight(float delta) {
        isDrag = true;
        if (onPullDownListener != null) {
            onPullDownListener.pullDownDeltaY(delta, getHeight());
        }
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        this.onScrollListener = l;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (onScrollListener != null) {
            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void setOnPullDownListener(OnPullDownListener listener) {
        onPullDownListener = listener;
    }

    public interface OnPullDownListener{
        void pullDownDeltaY(float deltaY, int height);
    }
}

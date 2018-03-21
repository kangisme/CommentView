package com.kang.commentview;

import java.util.List;

import com.orhanobut.logger.Logger;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 仿今日头条可下拉关闭弹窗评论
 * Created by kangren on 2018/3/15.
 */

public class CommentView extends LinearLayout
{

    /**
     * 下拉复位距离，超过这个距离PopupWindow窗口关闭 单位dp
     */
    private final static int RESET_DISTANCE = 80;

    @BindView(R.id.comment_close)
    ImageView closeImg;

    @BindView(R.id.comment_num)
    TextView numText;

    @BindView(R.id.comment_list)
    ListView listView;

    private Context mContext;

    private List<String> lists;

    private OnClickListener onClickListener;

    /**
     * 用于完成回滚操作的实例
     */
    private Scroller mScroller;

    /**
     * ACTION_DOWN的Y坐标
     */
    private float mDownY;

    /**
     * 当前ACTION_MOVE的Y坐标
     */
    private float mMoveY;

    /**
     * 上一ACTION_MOVE的Y坐标
     */
    private float mLastMoveY;

    private int resetDistance;

    public CommentView(Context context, List<String> list)
    {
        super(context);
        mContext = context;
        lists = list;
        init();
    }

    private void init()
    {
        resetDistance = (int) (RESET_DISTANCE * mContext.getResources().getDisplayMetrics().density);
        mScroller = new Scroller(mContext);
        inflate(mContext, R.layout.comment_view, this);
        ButterKnife.bind(this);

        listView.setAdapter(new CommentAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (onClickListener != null)
                {
                    onClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    @OnClick(R.id.comment_close)
    public void onViewClicked(View view)
    {
        if (onClickListener != null)
        {
            onClickListener.onCloseClick(view);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        if (isFirstItemVisible())
        {
            switch (ev.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    mDownY = ev.getRawY();
                    mLastMoveY = mDownY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    mMoveY = ev.getRawY();
                    float diff = mMoveY - mLastMoveY;
                    mLastMoveY = mMoveY;
                    if (diff > 0)
                    {
                        Logger.d("true");
                        return true;
                    }
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getRawY();
                int scrolledY = (int) (mLastMoveY - mMoveY);
                scrollBy(0, scrolledY);
                mLastMoveY = mMoveY;
                onClickListener.onScrolling(getBgAlpha(getScrollY()));
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollY() < -resetDistance)
                {
                    if (onClickListener != null)
                    {
                        onClickListener.onCloseClick(null);
                        // 窗口关闭后复位，不然下次弹出可能会保留上次滑动的位置
                        scrollTo(0, 0);
                    }
                }
                else
                {
                    // 弹性复位
                    mScroller.startScroll(0, getTop() + getScrollY(), 0, -getScrollY(), 500);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll()
    {
        // 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset())
        {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 判断第一个child是否完全显示出来,即是否触顶
     * 
     * @return true完全显示出来，否则false
     */
    private boolean isFirstItemVisible()
    {
        final Adapter adapter = listView.getAdapter();

        if (null == adapter || adapter.isEmpty())
        {
            return true;
        }
        // 第一个可见item在ListView中的位置
        if (listView.getFirstVisiblePosition() == 0)
        {
            // getChildCount是当前屏幕可见范围内的count
            int mostTop = (listView.getChildCount() > 0) ? listView.getChildAt(0).getTop() : 0;
            if (mostTop >= 0)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据当前滚动偏移值，计算背景透明度
     * 
     * @param scrollY Y方向偏移值
     * @return alpha
     */
    private float getBgAlpha(float scrollY)
    {
        return (float) (0.6 * (-scrollY) / getHeight() + 0.4);
    }

    /**
     * 点击事件接口抛出
     * 
     * @param listener listener
     */
    public void setOnClickListener(OnClickListener listener)
    {
        onClickListener = listener;
    }

    public interface OnClickListener
    {
        void onCloseClick(View view);

        void onItemClick(AdapterView<?> parent, View view, int position, long id);

        void onScrolling(float bgAlpha);
    }

    private class CommentAdapter extends BaseAdapter
    {

        public CommentAdapter()
        {
            super();
        }

        @Override
        public int getCount()
        {
            return lists.size();
        }

        @Override
        public Object getItem(int position)
        {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder = null;
            if (convertView == null)
            {
                convertView = View.inflate(mContext, R.layout.comment_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(lists.get(position));
            return convertView;
        }
    }

    private class ViewHolder
    {
        TextView textView;

        public ViewHolder(View itemView)
        {
            textView = itemView.findViewById(R.id.item_text);
        }
    }
}

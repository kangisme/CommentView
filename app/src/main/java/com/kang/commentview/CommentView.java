package com.kang.commentview;

import java.util.List;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

/**
 * Created by kangren on 2018/3/13.
 */

public class CommentView extends RelativeLayout {

    private Context mContext;

    private ListView listView;

    private List<String> lists;

    /**
     * ListView是否触顶
     */
    private boolean isReachTop;

    /**
     * 是否是下拉
     */
    private boolean isPullDown;

    private boolean isFirstTouch;

    private PointF fingerDown;

    private PointF fingerMove;

    private PointF fingerUp;

    public CommentView(Context context, List<String> list) {
        super(context);
        mContext = context;
        lists = list;
        init();
    }

    private void init() {
        fingerDown = new PointF();
        fingerMove = new PointF();
        fingerUp = new PointF();

        inflate(mContext, R.layout.popup_comment, this);
        listView = findViewById(R.id.comment_list);
        listView.setAdapter(new CommentAdapter());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isReachTop = firstVisibleItem == 0;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isReachTop) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Logger.d("action_down: " + event.getX() + "---" + event.getY());
                fingerDown.x = event.getX();
                fingerDown.y = event.getY();

                break;
            case MotionEvent.ACTION_UP:
//                Logger.d("action_up: " + event.getX() + "---" + event.getY());
                fingerUp.x = event.getX();
                fingerUp.y = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
//                Logger.d("action_move: " + event.getX() + "---" + event.getY());
                fingerMove.x = event.getX();
                fingerMove.y = event.getY();
                float tanx = (fingerMove.y - fingerDown.y) / (fingerMove.x - fingerDown.x);
                Logger.d(tanx);
                if (tanx <= -1 || tanx >= 1) {
                    isPullDown = true;
                } else {
                    isPullDown = false;
                    listView.onTouchEvent(event);
                }
                break;
        }

        return true;
    }

    private class CommentAdapter extends BaseAdapter {

        public CommentAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public Object getItem(int position) {
            return lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.comment_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(lists.get(position));
            return convertView;
        }
    }

    private class ViewHolder{
        TextView textView;

        public ViewHolder(View itemView) {
            textView = itemView.findViewById(R.id.item_text);
        }
    }

}

package com.kang.commentview;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

/**
 * Created by kangren on 2018/3/13.
 */

public class CommentPopup extends PopupWindow {

    private Context mContext;

    private List<String> lists;

    private CommentListView listView;

    public CommentPopup(Context context) {
        super(context);
        this.mContext = context;
        lists = new ArrayList<>();
        lists.add("第一个");
        lists.add("第2个");
        lists.add("第3个");
        lists.add("第4个");
        lists.add("第5个");
        lists.add("第6个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        lists.add("第7个");
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.popup_comment, null);
        listView = view.findViewById(R.id.comment_list);
        listView.setAdapter(new CommentAdapter());
        listView.setOnPullDownListener(new CommentListView.OnPullDownListener() {
            @Override
            public void pullDownDeltaY(float deltaY, int height) {
                update(0, 0, -1, (int) (height - deltaY), true);
            }
        });

        this.setContentView(view);

        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xFFFFFFFF);

        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
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

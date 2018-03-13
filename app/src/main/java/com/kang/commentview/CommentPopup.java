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

    private CommentView commentView;

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
        commentView = new CommentView(mContext, lists);

        this.setContentView(commentView);

        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popwin_anim_style);

        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xEEFFFFFF);

        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }
}

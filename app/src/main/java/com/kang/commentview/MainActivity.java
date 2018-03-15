package com.kang.commentview;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    @BindView(R.id.web_view)
    WebView webView;

    @BindView(R.id.send_comment)
    View sendComment;

    @BindView(R.id.container)
    LinearLayout container;

    @BindView(R.id.comment_container)
    FrameLayout commentContainer;

    private PopupWindow popupWindow;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        webView.loadUrl("http://www.baidu.com");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        List<String> lists = new ArrayList<>();
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

        CommentView view = new CommentView(this, lists);
        view.setOnClickListener(new CommentView.OnClickListener() {
            @Override
            public void onCloseClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Click " + position, Toast.LENGTH_SHORT).show();
            }
        });
        popupWindow = new PopupWindow(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        //设置SelectPicPopupWindow弹出窗体的高
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置SelectPicPopupWindow弹出窗体可点击
        popupWindow.setFocusable(true);

        //设置SelectPicPopupWindow弹出窗体动画效果
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        //实例化一个ColorDrawable颜色为透明
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);

        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
    }

    @OnClick(R.id.send_comment)
    public void onViewClicked() {
        popupWindow.showAtLocation(commentContainer, Gravity.BOTTOM, 0, 0);
    }
}

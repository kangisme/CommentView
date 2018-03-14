package com.kang.commentview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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

    private CommentPopup commentPopup;

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
    }

    @OnClick(R.id.send_comment)
    public void onViewClicked() {
//        commentPopup = new CommentPopup(this);
//        commentPopup.showAtLocation(commentContainer, Gravity.BOTTOM, 0, 0);
        startActivity(new Intent(this, ScrollActivity.class));
    }
}

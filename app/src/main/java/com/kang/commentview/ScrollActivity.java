package com.kang.commentview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by kangren on 2018/3/14.
 */

public class ScrollActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        TextView text = (TextView) findViewById(R.id.text);

        // 测试数据集。
        String s = "";
        for (int i = 0; i < 10; i++) {
            s += i + "\n";
        }

        text.setText(s);

        // 设置TextView的背景颜色，更容易观察出弹性回弹效果。
        text.setBackgroundColor(Color.RED);
    }
}

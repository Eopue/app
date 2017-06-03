package com.example.sanstoi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.example.sanstoi.myapplication.R;

public class LineActivity extends AppCompatActivity {

    private EditText lineOne;
    private EditText lineTwo;
    private EditText lineThree;
    private TextView lineText;
    private Button submit;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        init();
    }

    private void init() {

        Intent intent = new Intent();
        String stringValue = intent.getStringExtra("lineText");

        lineOne = (EditText) findViewById(R.id.lineOne);
        lineTwo = (EditText) findViewById(R.id.lineTwo);
        lineThree = (EditText) findViewById(R.id.lineThree);
        lineText = (TextView) findViewById(R.id.lineText);
        submit = (Button) findViewById(R.id.line_btn);
        cancel = (Button) findViewById(R.id.line_cancel);

        lineText.setText(getResources().getString(R.string.child_scenic_added) + stringValue);
        lineText.setText("test");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lineOneText = lineOne.getText().toString();
                String lineTwoText = lineTwo.getText().toString();
                String lineThreeText = lineThree.getText().toString();
                Map<String, String> map = new HashMap<String, String>();
                if (!"".equals(lineOneText)) {
                    map.put("lineOne", lineOneText);
                }
                if (!"".equals(lineTwoText)) {
                    map.put("lineTwo", lineTwoText);
                }
                if (!"".equals(lineThreeText)) {
                    map.put("lineThree", lineThreeText);
                }

                Intent intent = new Intent();
                intent.putExtra("resultMap", (Serializable) map);
                LineActivity.this.setResult(2, intent);// 设置回传数据。resultCode值是2，这个值在主窗口将用来区分回传数据的来源，以做不同的处理
                LineActivity.this.finish();// 关闭子窗口ChildActivity
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineOne.setText("");
                lineTwo.setText("");
                lineThree.setText("");
            }
        });
    }
}

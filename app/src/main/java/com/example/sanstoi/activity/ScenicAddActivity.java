package com.example.sanstoi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanstoi.bean.ChildScenic;
import com.example.sanstoi.bean.ParentScenic;
import com.example.sanstoi.connet.ConnectToWebService;
import com.example.sanstoi.myapplication.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ScenicAddActivity extends AppCompatActivity {

    private TextView tv = null;
    private TextView tv2 = null;
    private TextView tv3 = null;
    private ParentScenic parentScenic = null;
    private Button btn_childAdd = null;
    private Button btn_submit = null;
    private Button btn_line_add = null;
    ConnectToWebService connectToWebService = new ConnectToWebService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_add);

        init();
    }

    private void init() {
        tv = (TextView) findViewById(R.id.parent_text);
        Bundle bundle = (Bundle) getIntent().getExtras().get("bundle");
        parentScenic = (ParentScenic) bundle.getParcelable("parentScenic");
        tv.setText(getResources().getString(R.string.parent_scenic_selected) + parentScenic.getParentScenicName());
        btn_childAdd = (Button) findViewById(R.id.child_add_button);
        btn_submit = (Button) findViewById(R.id.submit);
        btn_line_add = (Button) findViewById(R.id.line_btn);
        tv2 = (TextView) findViewById(R.id.child_text);
        tv3 = (TextView) findViewById(R.id.line_text);

        btn_childAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ScenicAddActivity.this, ChildScenicAddActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        btn_line_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lineText", "test");
                intent.setClass(ScenicAddActivity.this, LineActivity.class);
                startActivityForResult(intent, 3);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveScenic();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:         // 子窗口ChildActivity的回传数据
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        //处理代码在此地
                        ChildScenic childScenic = (ChildScenic) bundle.get("result");
                        parentScenic.setChildsenics(childScenic);
                        StringBuilder strs = new StringBuilder();
                        for (ChildScenic child : parentScenic.getChildsenics()) {
                            strs.append(child.getChildScenicName());
                            strs.append(",");
                        }

                        tv2.setText(getResources().getString(R.string.child_scenic_added) + strs.substring(0, strs.length() - 1).toString());
                    }
                }
                break;
            case 2:
                Intent intent = new Intent();
                HashMap<String, String> map = (HashMap<String, String>) intent.getSerializableExtra("resultMap");
                if (map.isEmpty()) {
                    tv3.setText(R.string.line_empty);
                    return;
                }
                tv3.setText(getResources().getString(R.string.line_total) + map.size());
                for (String key : map.keySet()) {
                    if (key.equals("lineOne")) {
                        parentScenic.setLine1(map.get(key));
                    } else if (key.equals("lineTwo")) {
                        parentScenic.setLine2(map.get(key));
                    } else {
                        parentScenic.setLine3(map.get(key));
                    }
                }
                break;
            default:
                //其它窗口的回传数据
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveScenic() {
        try {
            boolean result = connectToWebService.saveScenic(parentScenic);
            if (result == true) {
                Toast.makeText(this, R.string.scenic_add_success, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(this, HomeActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, R.string.scenic_add_error, Toast.LENGTH_SHORT).show();
        }
    }
}

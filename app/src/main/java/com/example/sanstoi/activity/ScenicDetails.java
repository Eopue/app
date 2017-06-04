package com.example.sanstoi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanstoi.bean.ParentScenic;
import com.example.sanstoi.myapplication.MapActivity;
import com.example.sanstoi.myapplication.R;
import com.example.sanstoi.util.Base64Util;
import com.example.sanstoi.util.PicassoUtil;
import com.example.sanstoi.util.Speak;


public class ScenicDetails extends AppCompatActivity {

    private ParentScenic parentScenic = null;
    private ImageView tv_img = null;
    private TextView tv_brief = null;
    private Switch sw_speak = null;
    private Button btn_begin = null;
    private Speak speak = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_details);
        speak = new Speak(getApplication());
        init();
    }

    public void init() {

        sw_speak = (Switch) findViewById(R.id.sw_speak);
        tv_img = (ImageView) findViewById(R.id.scenic_detail_img);
        tv_brief = (TextView) findViewById(R.id.scenic_detail_biref);
        btn_begin = (Button) findViewById(R.id.begin);
        Bundle bundle = (Bundle) getIntent().getExtras().get("bundle");
        parentScenic = (ParentScenic) bundle.getParcelable("ScenicDetail");
        PicassoUtil.loadImageView(this, parentScenic.getImage(),1080, 500, tv_img);
        tv_brief.setText(parentScenic.getParentScenicBrief());
        sw_speak.setChecked(false);
        sw_speak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sw_speak.setSelected(!sw_speak.isSelected());
                if(sw_speak.isSelected()){
                    speak.startSpeak(parentScenic.getParentScenicBrief());
                }else{
                    speak.endSpeak();
                }
            }
        });

        btn_begin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                Intent intent = new Intent();
                bundle.putParcelable("parentScenic", parentScenic);
                intent.setClass(getApplication(), MapActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

    }
}
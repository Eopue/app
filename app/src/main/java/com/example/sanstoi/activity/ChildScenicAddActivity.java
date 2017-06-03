package com.example.sanstoi.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.sanstoi.bean.ChildScenic;
import com.example.sanstoi.constants.Constants;
import com.example.sanstoi.myapplication.R;
import com.example.sanstoi.util.Base64Util;
import com.example.sanstoi.util.PicassoUtil;

import java.util.Calendar;
import java.util.TimeZone;

public class ChildScenicAddActivity extends AppCompatActivity {

    private EditText childScenicName;
    private EditText childScenicPrice;
    private Spinner childScenicOpenDate;
    private Button childScenicStartTime;
    private Button childScenicEndTime;
    private EditText child_content;
    private EditText mLongtitude;
    private EditText mLatitude;
    private Button btn_submit;
    private Button btn_cancel;
    private Button btn_image_up;
    private ImageView iv_img;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_scenic_add);

        init();
    }

    private void init(){
        childScenicName = (EditText) findViewById(R.id.childScenicName);
        childScenicPrice = (EditText) findViewById(R.id.childScenicPrice);
        childScenicOpenDate = (Spinner) findViewById(R.id.childScenicOpenDate);
        childScenicStartTime = (Button) findViewById(R.id.childScenicStartTime);
        childScenicEndTime = (Button) findViewById(R.id.childScenicEndTime);
        child_content = (EditText) findViewById(R.id.child_content);
        btn_submit = (Button)findViewById(R.id.submit);
        btn_cancel = (Button)findViewById(R.id.clear);
        mLatitude = (EditText)findViewById(R.id.mLatitude);
        mLongtitude= (EditText)findViewById(R.id.mLongtitude);
        iv_img = (ImageView)findViewById(R.id.image_up_child);
        btn_image_up = (Button)findViewById(R.id.way_picture_child);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChildScenic childScenic = new ChildScenic();
                childScenic.setChildScenicName(childScenicName.getText().toString());
                String childPrice = childScenicPrice.getText().toString();
                if(childPrice.matches(Constants.doubleTest)){
                    childScenic.setChildScenicPrice(Double.valueOf(childPrice));
                    childScenicPrice.setTextColor(0xFF000000);
                }else{
                    childScenicPrice.setText(R.string.error_price);
                    childScenicPrice.setTextColor(0xFFFF0000);
                    return;
                }
                childScenic.setChildScenicPrice(Double.valueOf(childScenicPrice.getText().toString()));
                childScenic.setChildScenicOpenDate(childScenicOpenDate.getSelectedItem().toString());
                childScenic.setChildScenicStartTime(childScenicStartTime.getText().toString());
                childScenic.setChildScenicEndTime(childScenicEndTime.getText().toString());
                childScenic.setChildScenicBrief(child_content.getText().toString());
                String longtitude = mLongtitude.getText().toString();
                if(longtitude.matches(Constants.doubleTest)){
                    childScenic.setmLongtitude(Double.valueOf(longtitude));
                    mLongtitude.setTextColor(0xFF000000);
                }else{
                    mLongtitude.setText(R.string.error_longtitude);
                    mLongtitude.setTextColor(0xFFFF0000);
                    return;
                }
                String latitude = mLatitude.getText().toString();
                if(latitude.matches(Constants.doubleTest)){
                    childScenic.setmLatitude(Double.valueOf(latitude));
                    mLatitude.setTextColor(0xFF000000);
                }else{
                    mLatitude.setText(R.string.error_latitude);
                    mLatitude.setTextColor(0xFFFF0000);
                    return;
                }
                childScenic.setImage(btn_image_up.getText().toString());
                Intent intent = new Intent();
                intent.putExtra("result",childScenic);
                ChildScenicAddActivity.this.setResult(1, intent);// 设置回传数据。resultCode值是1，这个值在主窗口将用来区分回传数据的来源，以做不同的处理
                ChildScenicAddActivity.this.finish();// 关闭子窗口ChildActivity
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                childScenicName.setText("");
                childScenicPrice.setText("");
                childScenicStartTime.setText(R.string.scenic_time);
                childScenicEndTime.setText(R.string.scenic_time);
                child_content.setText("");
                mLongtitude.setText("");
                mLatitude.setText("");
                btn_image_up.setText("");
                iv_img.setImageBitmap(null);
                btn_image_up.setVisibility(View.VISIBLE);
            }
        });

        childScenicStartTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                time_init1();
                timePickerDialog.show();
            }
        });

        childScenicEndTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                time_init2();
                timePickerDialog.show();
            }
        });
        btn_image_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // 开启一个带有返回值的Activity
                startActivityForResult(intent, 3);
            }
        });
    }
    private void time_init1(){
        TimePickerDialog.OnTimeSetListener otsl=new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                childScenicStartTime.setText(hourOfDay+":"+getMinute(minute));
                timePickerDialog.dismiss();
            }
        };
        getCalender(otsl);
    }

    private void time_init2(){
        TimePickerDialog.OnTimeSetListener otsl=new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                childScenicEndTime.setText(hourOfDay+":"+getMinute(minute));
                timePickerDialog.dismiss();
            }
        };
        getCalender(otsl);
    }

    private void getCalender(TimePickerDialog.OnTimeSetListener otsl){
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
        int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this,otsl,hourOfDay,minute,true);
    }

    private String getMinute(int minute){
        if(minute < 10){
            return "0"+ minute;
        }
        return ""+ minute;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                String str = Base64Util.imageToBase64(uri, this);
                btn_image_up.setText(str);
                btn_image_up.setVisibility(View.GONE);

                PicassoUtil.loadImageView(this, uri, iv_img);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

package com.example.sanstoi.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.sanstoi.bean.ParentScenic;
import com.example.sanstoi.constants.Constants;
import com.example.sanstoi.myapplication.R;
import com.example.sanstoi.util.Base64Util;
import com.example.sanstoi.util.PicassoUtil;

import java.util.Calendar;
import java.util.TimeZone;

public class ParentScenicAddActivity extends AppCompatActivity {

    private Spinner parentScenicProvince = null;
    private Spinner parentScenicCity = null;
    private EditText parentScenicName = null;
    private EditText parentScenicPrice = null;
    private Spinner parentScenicOpenDate = null;
    private Button parentScenicStartTime = null;
    private Button parentScenicEndTime = null;
    private EditText content = null;
    private Button btn_submit = null;
    private Button btn_cancel = null;
    private Button btn_way_picture = null;
    private ImageView iv_img = null;
    private TimePickerDialog timePickerDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_scenic_add);

        init();
    }

    private void init() {
        parentScenicProvince = (Spinner)findViewById(R.id.parentScenicProvince);
        parentScenicCity = (Spinner)findViewById(R.id.parentScenicCity);
        parentScenicName = (EditText)findViewById(R.id.parentScenicName);
        parentScenicPrice = (EditText)findViewById(R.id.parentScenicPrice);
        parentScenicOpenDate = (Spinner) findViewById(R.id.parentScenicOpenDate);
        parentScenicStartTime = (Button)findViewById(R.id.parentScenicStartTime);
        parentScenicEndTime = (Button)findViewById(R.id.parentScenicEndTime);

        content = (EditText)findViewById(R.id.content);
        btn_submit = (Button)findViewById(R.id.submit);
        btn_cancel = (Button)findViewById(R.id.clear);
        btn_way_picture = (Button)findViewById(R.id.way_picture);
        iv_img = (ImageView)findViewById(R.id.image_up);

        parentScenicProvince.setOnItemSelectedListener(new spinnerItemSelected());

        parentScenicCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String city = parentScenicCity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentScenic parentScenic = new ParentScenic();
                parentScenic.setParentScenicProvince(parentScenicProvince.getSelectedItem().toString());
                parentScenic.setParentScenicCity(parentScenicCity.getSelectedItem().toString());
                parentScenic.setParentScenicName(parentScenicName.getText().toString());
                String price = parentScenicPrice.getText().toString();
                if(price.matches(Constants.doubleTest)){
                    parentScenic.setParentScenicPrice(Double.valueOf(price));
                    parentScenicPrice.setTextColor(0xFF000000);
                }else{
                    parentScenicPrice.setText(R.string.error_price);
                    parentScenicPrice.setTextColor(0xFFFF0000);
                    return;
                }
                parentScenic.setParentScenicOpenDate(parentScenicOpenDate.getSelectedItem().toString());
                parentScenic.setParentScenicStartTime(parentScenicStartTime.getText().toString());
                parentScenic.setParentScenicEndTime(parentScenicEndTime.getText().toString());
                parentScenic.setParentScenicBrief(content.getText().toString());
                parentScenic.setImage(btn_way_picture.getText().toString());
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putParcelable("parentScenic", parentScenic);
                intent.setClass(ParentScenicAddActivity.this,ScenicAddActivity.class);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View v){
              parentScenicName.setText("");
              parentScenicPrice.setText("");
              parentScenicStartTime.setText("0:00");
              parentScenicEndTime.setText("0:00");
              iv_img.setImageBitmap(null);
              btn_way_picture.setText(R.string.way_picture);
              btn_way_picture.setVisibility(View.VISIBLE);
              content.setText("");
          }
        });
        parentScenicStartTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(timePickerDialog==null){
                    time_init1();
                }
                timePickerDialog.show();
            }
        });
        parentScenicEndTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                time_init2();
                timePickerDialog.show();
            }
        });
        btn_way_picture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 开启一个带有返回值的Activity
                    startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                String str = Base64Util.imageToBase64(uri, this);
                btn_way_picture.setText(str);
                btn_way_picture.setVisibility(View.GONE);

                PicassoUtil.loadImageView(this, uri, iv_img);
                }
            }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void time_init1(){
        TimePickerDialog.OnTimeSetListener otsl=new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                parentScenicStartTime.setText(hourOfDay+":"+getMinute(minute));
                timePickerDialog.dismiss();
            }
        };
       getCalender(otsl);
    }

    private void time_init2(){
        TimePickerDialog.OnTimeSetListener otsl=new TimePickerDialog.OnTimeSetListener(){
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                parentScenicEndTime.setText(hourOfDay+":"+getMinute(minute));
                timePickerDialog.dismiss();
            }
        };
        getCalender(otsl);
    }

    private String getMinute(int minute){
        if(minute < 10){
            return "0"+ minute;
        }
        return ""+ minute;
    }

    private void getCalender(TimePickerDialog.OnTimeSetListener otsl){
        Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
        int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this,otsl,hourOfDay,minute,true);
    }

    // 二级联动adapter
    class spinnerItemSelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Spinner spinner = (Spinner) parent;
            //String pro = (String) spinner.getItemAtPosition(position);
            String tProvince = parentScenicProvince.getSelectedItem().toString().substring(0,2);
            // 处理省的市的显示
            ArrayAdapter<CharSequence> cityadapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.def,
                    R.layout.simple_spinner_item);
            if (tProvince.equals("北京")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.beijing,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("天津")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.tianjing,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("河北")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.hebei,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("山西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.shanxi,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("内蒙")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.neimeng,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("辽宁")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.liaoning,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("吉林")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jilin,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("黑龙")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.heilongjjiang,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("上海")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.shanghai,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("江苏")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jiangsu,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("浙江")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.zhejiang,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("安徽")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.anhui,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("福建")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.fujian,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("江西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.jiangxi,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("山东")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.shandong,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("河南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.henan,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("湖北")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.hubei,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("湖南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.hunan,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("广东")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.guangdong,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("广西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.guangxi,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("海南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.hainan,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("重庆")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.chongqing,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("四川")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.sichuan,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("贵州")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.guizhou,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("云南")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.yunan,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("西藏")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.xizang,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("陕西")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.shan3xi,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("甘肃")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.gansu,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("青海")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.qinghai,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("宁夏")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.ningxia,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("新疆")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.xinjiang,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("台湾")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.taiwan,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("香港")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.xianggang,
                        R.layout.simple_spinner_item);
            } else if (tProvince.equals("澳门")) {
                cityadapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.aomen,
                        R.layout.simple_spinner_item);
            }
            parentScenicCity.setAdapter(cityadapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}

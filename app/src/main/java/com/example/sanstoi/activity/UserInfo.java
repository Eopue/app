package com.example.sanstoi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanstoi.myapplication.R;
public class UserInfo extends Fragment {

    private TextView now_name;
    private ImageView now_image;
    private Button exit;
    private Button scenic_add;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        now_name=(TextView)view.findViewById(R.id.usernow_name);
        now_image=(ImageView)view.findViewById(R.id.usernow_image);
        scenic_add=(Button)view.findViewById(R.id.manage_scenic);
        exit=(Button)view.findViewById(R.id.exit);
        sp=getActivity().getApplication().getSharedPreferences("userNow",0);
        String name = sp.getString("name","");
        if(!TextUtils.isEmpty(name)){
            now_name.setText(getResources().getString(R.string.welcome)+name);
            scenic_add.setVisibility(View.VISIBLE);
        }
        else{
            now_name.setText(R.string.login_or_register);
        }

        now_image.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity().getApplication(),LoginActivity.class);
                startActivity(intent);
            }
        });

        scenic_add.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity().getApplication(),ParentScenicAddActivity.class);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                scenic_add.setVisibility(View.GONE);
                sp.edit().clear().commit();
                Intent intent = new Intent();
                intent.setClass(getActivity().getApplication(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

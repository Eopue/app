package com.example.sanstoi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanstoi.bean.User;
import com.example.sanstoi.connet.ConnectToWebService;
import com.example.sanstoi.myapplication.R;

public class RegisterActivity extends AppCompatActivity {

    private User user;
    private Button btn_submit;
    private Button btn_cancel;
    private EditText nicknameView;
    private EditText pwdView;
    private EditText repwdView;
    private ConnectToWebService connectToService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init(){

        btn_submit = (Button) findViewById(R.id.register_submit);
        btn_cancel = (Button) findViewById(R.id.register_cancel);
        nicknameView = (EditText) findViewById(R.id.register_nick);
        pwdView = (EditText) findViewById(R.id.register_pwd);
        repwdView = (EditText) findViewById(R.id.register_repwd);
        connectToService = new ConnectToWebService();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setNickname(nicknameView.getText().toString());
                user.setUserpwd(pwdView.getText().toString());
                saveUser(user);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nicknameView.setText("");
                pwdView.setText("");
            }
        });
    }

    private void saveUser(User user){
        try{

            if(user.getNickname().toString().equals("")|| user.getUserpwd().toString().equals("") ){
                Toast.makeText(this, R.string.register_nickname_pwd_validate, Toast.LENGTH_SHORT).show();
                nicknameView.setText("");
                pwdView.setText("");
                return;
            }
            else if(!user.getUserpwd().equals(repwdView.getText().toString())){
                Toast.makeText(this, R.string.register_pwd_not_equals, Toast.LENGTH_SHORT).show();
                pwdView.setInputType(0);
                repwdView.setInputType(0);
                return;
            }
            boolean result = connectToService.saveUser(user);
            if(result == true){
                Toast.makeText(this, R.string.register_result_success, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, R.string.register_result_error, Toast.LENGTH_SHORT).show();
            }

        } catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, R.string.connect_server_fail, Toast.LENGTH_SHORT).show();
        }

    }
}

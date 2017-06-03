package com.example.sanstoi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanstoi.bean.User;
import com.example.sanstoi.myapplication.R;
import com.example.sanstoi.myapplication.MapActivity;
import com.example.sanstoi.connet.ConnectToWebService;

public class LoginActivity extends AppCompatActivity {

    private TextView textView;
    private Button login_btn;
    private EditText userIdView;
    private EditText userpwdView;
    private Button register_btn;
    private Intent intent;
    private ConnectToWebService connectToService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()

                .detectDiskReads()

                .detectDiskWrites()

                .detectNetwork() // or .detectAll() for all detectable problems

                .penaltyLog()

                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()

                .detectLeakedSqlLiteObjects()

                .detectLeakedClosableObjects()

                .penaltyLog()

                .penaltyDeath()

                .build());

        textView = (TextView) findViewById(R.id.text);
        userIdView = (EditText) findViewById(R.id.usernickname);
        userpwdView = (EditText) findViewById(R.id.userpwd) ;
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.login_register);
        connectToService = new ConnectToWebService();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickname = userIdView.getText().toString();
                String userpwd = userpwdView.getText().toString();
                if ("".equals(userpwd) || "".equals(nickname)) {
                    // 给出错误提示
                    textView.setText(R.string.login_null);
                    //清空输入框
                    userIdView.setText("");
                    userpwdView.setText("");
                    return;
                }
                login(nickname,userpwd);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                /*此处用Intent来实现Activity与Activity之间的跳转*/
                intent=new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                //Intent intent=new Intent(IntentTest.this,MyActivity.class);
                startActivity(intent);
            }
        });
    }
    private void login(String nickname,String userpwd){
        try{
            User user = connectToService.login(nickname,userpwd);
            if(user != null){
                Toast.makeText(this, R.string.login_result_success, Toast.LENGTH_SHORT).show();
                SharedPreferences sp=getSharedPreferences("userNow",0);
                SharedPreferences.Editor editor=sp.edit();
                //把数据进行保存
                editor.putString("name",user.getNickname());
                editor.putString("password",user.getUserpwd());
                editor.putInt("userId",user.getUserId());
                //提交数据
                editor.commit();
                intent=new Intent();
                intent.setClass(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, R.string.login_result_error, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            Toast.makeText(this, R.string.connect_server_fail, Toast.LENGTH_SHORT).show();
        }
    }
}

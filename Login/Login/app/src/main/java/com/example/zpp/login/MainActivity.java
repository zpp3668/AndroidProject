package com.example.zpp.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button  btn_exit;
    private Button btn_login;
    private int times=3;
    private String str="可尝试的剩余次数：",username="",password="";
    private TextView textView_times;
    private EditText text_userName,text_passWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_exit =(Button)findViewById(R.id.button_exit ) ;
        btn_login =(Button)findViewById(R.id.button_login ) ;
        btn_exit .setOnClickListener(this) ;
        btn_login .setOnClickListener(this) ;
        System.out.println("初始化成功");
    }

    @Override
    public void onClick(View v) {
        //超过三次禁止登录
        if (times == 0) { //disble the button, close the application e.t.c
            btn_login.setEnabled(false);
            System.out.println("按钮禁止");
        }
        //登录
        if (v == btn_login) {
            if (text_userName.getText().toString().equals("admin") && text_passWord.getText().toString().equals("admin")) {
                //correcct password可以用对话框进行信息提示
                //MyDialog myDialog =new MyDialog(this,"登录成功");
                //myDialog.show();
                System.out.println("登录成功");
            } else { //wrong password可以用对话框进行信息提示
                System.out.println("密码错误");
                this.times--;
                textView_times.setText(str+times);
            }
        }

        //退出
        if (v == btn_exit) {
            System.out.println("退出");
            System.exit(0);
        }
    }
}

package com.example.zpp.phoneandmessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.telephony.SmsManager;
import android.view.View.OnLongClickListener;

public class MainActivity extends Activity implements OnLongClickListener {

    private EditText text_phonenumber;
    private EditText text_message;
    private Button btn_call;
    private Button btn_send;
    private OnClickListener listener=new OnClickListener() {
        @Override
        public void onClick(View action) {
            //拨打电话事件
            if(action==btn_call){
                Intent intent=new Intent(Intent.ACTION_DIAL);//隐式
                String number=text_phonenumber.getText().toString();
                //传送电话号码
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
            //发送短信事件
            else if(action==btn_send){
                Intent intent=new Intent(Intent.ACTION_SENDTO);
                String number=text_phonenumber.getText().toString();
                String content=text_message.getText().toString();
                //传送电话和短信内容
                intent.setData(Uri.parse("smsto:"+number));
                intent.putExtra("sms_body", content);
                startActivity(intent);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_phonenumber=(EditText) findViewById(R.id.phonenumber);
        text_message=(EditText) findViewById(R.id.message);
        btn_call=(Button) findViewById(R.id.btn_call);
        btn_send=(Button) findViewById(R.id.btn_send);
        //监听事件
        btn_call.setOnClickListener(listener);
        btn_send.setOnClickListener(listener);
        btn_call.setOnLongClickListener(this);
        btn_send.setOnLongClickListener(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean onLongClick(View view) {
        if(view==btn_call){
            Intent intent=new Intent(Intent.ACTION_CALL);
            String number =text_phonenumber.getText().toString();
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }
        else if(view==btn_send){
            SmsManager smsManager=SmsManager.getDefault();
            String number=text_phonenumber.getText().toString();
            String content=text_message.getText().toString();
            smsManager.sendTextMessage(number, null,
                    content, null, null);
        }
        return true;
    }
}

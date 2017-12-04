package com.example.hao.myexperiment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final String SMS_ACTION = "com.android.TinySMS.RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private SentReceiver receiver = new SentReceiver();

    public void onStartClicked(View v){
        EditText addressView = findViewById(R.id.editText);
        EditText contentView = findViewById(R.id.editText2);
        this.sendSMS(addressView.getText().toString(),contentView.getText().toString());
    }

    private class SentReceiver extends BroadcastReceiver {
        @Override public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SMS_ACTION)) {
                int code = getResultCode(); //短消息发送成功
                if(code == Activity.RESULT_OK) Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendSMS(String address, String content) {
        SmsManager manager = SmsManager.getDefault();
        Intent i = new Intent(SMS_ACTION); //生成PendingIntent，当消息发送完成，接收到广播
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
        manager.sendTextMessage( address, null, content, sentIntent, null);
    }


}

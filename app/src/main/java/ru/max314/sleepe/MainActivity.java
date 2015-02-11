package ru.max314.sleepe;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.tw.john.TWUtil;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends Activity {

    TWUtil twUtil;
    StringBuilder stringBuilder = new StringBuilder();
    TextView textView;
    Handler handler;
    public MainActivity() {

        handler = new Handler(){
            /**
             * Subclasses must implement this to receive messages.
             *
             * @param msg
             */
            @Override
            public void handleMessage(Message msg) {
                Logd("handleMessage:"+msg.toString());
                super.handleMessage(msg);
            }
        };
        Logd("ctor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logd("on create enter");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView2);
    }

    private void Logd(String s) {

        stringBuilder.append(s);
        Log.d(MainActivity.class.getName(),s);
    }


    public void onStart(View view) {
        Logd("on start");
        if (twUtil!=null){
            return;
        }

        twUtil = new TWUtil();
        Logd("new twutil");
        if (twUtil.open(new short[]{514})!=0){
            Logd("Error open twutil");
            twUtil = null;
            return;
        }
        Logd("open");
        twUtil.start();
        Logd("start");

        twUtil.addHandler("test",handler);
        Logd("add handler");
        twUtil.write(514, 3);
        Logd("write ");
    }
    // not work
//    public void onStart(View view) {
//        Logd("on start");
//        if (twUtil!=null){
//            return;
//        }
//
//        twUtil = new TWUtil();
//        Logd("new twutil");
//        if (twUtil.open(new short[]{517})!=0){
//            Logd("Error open twutil");
//            twUtil = null;
//            return;
//        }
//        Logd("open");
//        twUtil.start();
//        Logd("start");
//
//        twUtil.addHandler("test",handler);
//        Logd("add handler");
//        twUtil.write(517, 255);
//        Logd("write ");
//    }

    public void onStop(View view) {
        if (twUtil==null){
            return;
        }
        Logd("on stop ");
        twUtil.removeHandler("test");
        Logd("remove handler ");
        twUtil.stop();
        Logd("stop");
        twUtil.close();
        Logd("close");
        twUtil = null;
    }

    public void onInfo(View view) {
        String buff = stringBuilder.toString();
        textView.setText(buff);
    }
}


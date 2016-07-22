package com.weather_huangjiahao;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private TextView textView;

    private static final String TAG = "MainActivity";

    public  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String response = (String) msg.obj;
                    Log.d(TAG,response);
                    Today today = GsonDecode.todayDecode(response);
                    Log.d(TAG,today.getTemperature()+"Today");
                    List<Future> futures = GsonDecode.futureDecode(response);
                    if(futures == null) {
                        Log.d(TAG,"555555555");
                    }
                    Log.d(TAG,futures.get(0).getTemperature()+"Future");
                    break;
                case 1:
                    Log.d(TAG,"INTENTNET`````");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        textView = (TextView) findViewById(R.id.textView);

        String address = ToGetAddress.excute("广州");
        Log.d(TAG,"111111111111111111");
        VolleyRequest.sendVolleyRequest(address, queue, handler);
    }
}

package com.weather_huangjiahao;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;

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
                    Log.d(TAG,futures.get(0).getTemperature()+"Future");
                    break;
                case 1:
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
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PickProvinceActivity.class);
                startActivity(intent);
            }
        });

        //String address = ToGetAddress.excute("广州");
        //VolleyRequest.sendVolleyRequest(address, queue, handler);
    }
}

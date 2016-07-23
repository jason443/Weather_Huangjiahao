package com.weather_huangjiahao;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
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
        //queue = Volley.newRequestQueue(this);

        SharedPreferences preferences = getSharedPreferences("cityData", MODE_PRIVATE);
        int count = preferences.getInt("count",0);
        List<String> list = new ArrayList<>();
        for(int i=0; i<count+1; i++) {
            list.add(preferences.getString("data"+i,null));
        }

        /*
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PickProvinceActivity.class);
                startActivity(intent);
            }
        });

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list= new ArrayList<>();
                list.add("广州");
                list.add("惠州");
                list.add("武汉");
                list.add("成都");

                SharedPreferences.Editor editor = getSharedPreferences("cityData", MODE_PRIVATE).edit();
                int k = 0;
                for(int i=0; i<list.size();i++) {
                    editor.putString("data" + i,list.get(i));
                    k = i;
                }
                editor.putInt("count",k);
                editor.commit();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("cityData", MODE_PRIVATE);
                int count = preferences.getInt("count",0);
                List<String> list = new ArrayList<String>();
                for(int i=0; i<count+1; i++) {
                    list.add(preferences.getString("data"+i,null));
                }
                if(list.size() != 0) {
                    for(int i=0; i<list.size(); i++) {
                        Log.d("Main", list.get(i));
                    }
                }
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = "上海";
                SharedPreferences preferences = getSharedPreferences("cityData",MODE_PRIVATE);
                int count = preferences.getInt("count",0);
                List<String> list = new ArrayList<>();
                for(int i=0; i<count+1; i++) {
                    list.add(preferences.getString("data"+i,null));
                }
                count++;
                list.add(cityName);
                SharedPreferences.Editor editor = preferences.edit();
                for(int i=0; i< list.size(); i++) {
                    editor.putString("data"+i,list.get(i));
                }
                editor.putInt("count",count);
                editor.commit();
           }
        });

        //String address = ToGetAddress.excute("广州");
        //VolleyRequest.sendVolleyRequest(address, queue, handler);*/
    }
}

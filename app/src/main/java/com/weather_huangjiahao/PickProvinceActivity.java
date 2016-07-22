package com.weather_huangjiahao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2016/7/22.
 */
public class PickProvinceActivity extends Activity {

    private RequestQueue queue;
    private MyDatabaseHelper dbHelper;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 0:
                    dbHelper = new MyDatabaseHelper(PickProvinceActivity.this, "Place.db",null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    String response = (String)msg.obj;
                    List<Place> places = GsonDecode.provinceDecode(response);
                    for (int i = 0; i < places.size(); i++) {
                        values.put("province", places.get(i).getProvince());
                        db.insert("Province", null, values);
                        values.clear();
                    }
                    for (int i = 0; i < places.size(); i++) {
                        if (i == 0) {
                            String province = places.get(i).getProvince();
                            List<Place> tempList = new ArrayList<>();
                            for (int j = 0; j < places.size(); j++) {
                                if (places.get(j).getProvince().equals(province)) {
                                    tempList.add(places.get(i));
                                }
                            }
                            for (int k = 0; k < tempList.size(); k++) {
                                if (k == 0) {
                                    values.put("city", tempList.get(k).getCity());
                                    values.put("province", tempList.get(k).getProvince());
                                    db.insert("City", null, values);
                                    values.clear();
                                } else {
                                    if (!tempList.get(k).getCity().equals(tempList.get(k - 1).getCity())) {
                                        values.put("city", tempList.get(k).getCity());
                                        values.put("province", tempList.get(i).getProvince());
                                        db.insert("City", null, values);
                                        values.clear();
                                    }
                                }
                            }

                        } else {
                            if (!places.get(i).getProvince().equals(places.get(i - 1).getProvince())) {
                                String province = places.get(i).getProvince();
                                List<Place> tempList = new ArrayList<>();
                                for (int j = 0; j < places.size(); j++) {
                                    if (places.get(i).getProvince().equals(province)) {
                                        tempList.add(places.get(i));
                                    }
                                }
                                for(int k=0; k < tempList.size(); k++) {
                                    if (k == 0) {
                                        values.put("city", tempList.get(k).getCity());
                                        values.put("province", tempList.get(k).getProvince());
                                        db.insert("City", null, values);
                                        values.clear();
                                    } else {
                                        if (!tempList.get(k).getCity().equals(tempList.get(k - 1).getCity())) {
                                            values.put("city", tempList.get(k).getCity());
                                            values.put("province", tempList.get(i).getProvince());
                                            db.insert("City", null, values);
                                            values.clear();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 1:
                    flags--;
                    Toast.makeText(PickProvinceActivity.this,"网络有误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public static int flags = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_pickprovince);
        if(flags == 0){
            String address = "http://v.juhe.cn/weather/citys?key=932899bf88adaab75cebf1406bee49f6";
            VolleyRequest.sendVolleyRequest(address,queue,handler);
            flags++;
        }
    }

}

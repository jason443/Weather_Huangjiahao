package com.weather_huangjiahao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
                    dbHelper = MyDatabaseHelper.getInstance(PickProvinceActivity.this, "Place.db", null, 1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String response = (String) msg.obj;
                    db.beginTransaction();
                    try {
                        ContentValues values = new ContentValues();
                        List<Place> places = GsonDecode.provinceDecode(response);
                        for (int i = 0; i < places.size(); i++) {
                            values.put("province", places.get(i).getProvince());
                            db.insert("Province", null, values);
                            values.clear();
                        }

                        for (int i = 0; i < places.size(); i++) {
                            List<Place> cityList = GsonDecode.cityDecode(response, places.get(i).getProvince());
                            ContentValues contentValues = new ContentValues();
                            for (int k = 0; k < cityList.size(); k++) {
                                contentValues.put("city", cityList.get(k).getCity());
                                contentValues.put("province", cityList.get(k).getProvince());
                                db.insert("City", null, contentValues);
                                contentValues.clear();
                            }
                        }
                        db.setTransactionSuccessful();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        db.endTransaction();
                    }
                    Cursor cursor = db.query("City",null,"province = ?",new String[]{"广东"},null,null,null);
                    List<Place> places = new ArrayList<>();
                    if(cursor.moveToFirst()){
                        do{
                            String city = cursor.getString(cursor.getColumnIndex("city"));
                            String province = cursor.getString(cursor.getColumnIndex("province"));
                            places.add(new Place(province,city));
                        } while(cursor.moveToNext());
                    }
                    for(int i=0; i<places.size();i++) {
                        Log.d("PickProvince111",places.get(i).getProvince());
                        Log.d("PickProvince111",places.get(i).getCity());
                    }
                    break;
                case 1:
                    Toast.makeText(PickProvinceActivity.this,"网络有误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_pickprovince);

        dbHelper = MyDatabaseHelper.getInstance(PickProvinceActivity.this,"Place.de", null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Place> places = new ArrayList<>();
        Cursor cursor = db.query("City",null,"province = ?",new String[]{"广东"},null,null,null);
        if(cursor.moveToFirst()) {
            do{
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String province = cursor.getString(cursor.getColumnIndex("province"));
                places.add(new Place(province,city));
            } while (cursor.moveToNext());
        }
        if(places.size() > 0) {
            for(int i=0; i<places.size(); i++) {
                Log.d("PickProvince1",places.get(i).getProvince());
                Log.d("PickProvince1",places.get(i).getCity());
            }
        } else {
            Log.d("PickProvince2222","Internet");
            String address = "http://v.juhe.cn/weather/citys?key=932899bf88adaab75cebf1406bee49f6";
            VolleyRequest.sendVolleyRequest(address,queue,handler);
        }
    }

}

package com.weather_huangjiahao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2016/7/22.
 */
public class GsonDecode {

    public static Today todayDecode(String s) {
        Gson gson = new Gson();
        JsonBean jsonBean = gson.fromJson(s,JsonBean.class);
        return jsonBean.getResult().getToday();
    }

    public static List<Future> futureDecode(String s) {
        Gson gson = new Gson();
        JsonBean jsonBean = gson.fromJson(s,JsonBean.class);
        List<Future> futures = jsonBean.getResult().getFuture();
        return futures;
    }

    public static List<Place> provinceDecode(String s) {
        Gson gson = new Gson();
        List<Place> returnList = new ArrayList<>();
        JsonCityBean jsonCityBean = gson.fromJson(s, JsonCityBean.class);
        List<Place> places = jsonCityBean.getResult();
        for(int i=0; i<places.size(); i++) {
            if(i == 0) {
                returnList.add(places.get(i));
            } else {
                if(!places.get(i).getProvince().equals(places.get(i-1).getProvince())) {
                    returnList.add(places.get(i));
                }
            }
        }
        return returnList;
    }

    public static List<Place> cityDecode1(String s) {
        Gson gson = new Gson();
        List<Place> places;
        JsonCityBean jsonCityBean = gson.fromJson(s, JsonCityBean.class);
        places = jsonCityBean.getResult();
        return places;
    }

    public static List<Place> cityDecode(String s, String province) {
        List<Place> tempList = new ArrayList<>();
        List<Place> returnList = new ArrayList<>();
        Gson gson = new Gson();
        JsonCityBean jsonCityBean = gson.fromJson(s, JsonCityBean.class);
        List<Place> places = jsonCityBean.getResult();
        for(int i=0; i<places.size(); i++) {
            if(places.get(i).getProvince().equals(province)) {
                tempList.add(places.get(i));
            }
        }
        for(int i=0; i<tempList.size(); i++) {
            if(i == 0) {
                returnList.add(tempList.get(i));
            } else {
                if(!tempList.get(i).getCity().equals(tempList.get(i-1).getCity())) {
                    returnList.add(tempList.get(i));
                }
            }
        }
        return returnList;
    }

}

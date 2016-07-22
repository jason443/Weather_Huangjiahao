package com.weather_huangjiahao;

/**
 * Created by ASUS on 2016/7/22.
 */
public class Future {

    private String temperature;
    private String weather;
    private String week;
    private WeatherId weather_id;
    private String wind;
    private String date;

    public String getWind() {
        return wind;
    }

    public void setWeather_id(WeatherId weather_id) {
        this.weather_id = weather_id;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public WeatherId getWeather_id() {
        return weather_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String toString() {
        return "温度" + temperature + "天气" + weather + "日期" + week;
    }

}

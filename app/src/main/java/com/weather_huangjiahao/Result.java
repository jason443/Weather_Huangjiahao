package com.weather_huangjiahao;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by ASUS on 2016/7/22.
 */
public class Result {

    private Today today;
    private List<Future> future;

    public List<Future> getFuture() {
        return future;
    }

    public void setFuture(List<Future> future) {
        this.future = future;
    }

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }
}

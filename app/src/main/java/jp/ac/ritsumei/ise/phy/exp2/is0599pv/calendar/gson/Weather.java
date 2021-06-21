package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    public String status;  //返回是否成功返回值
    public Basic basic = new Basic();
    public Forecast forecast = new Forecast();
    public Suggestion suggestion = new Suggestion();
    public AQI aqi = new AQI();
    public Now now = new Now();
    @SerializedName("daily_forecast")
    public Forecast[] forecastList = new Forecast[7];  //解析出数组
}

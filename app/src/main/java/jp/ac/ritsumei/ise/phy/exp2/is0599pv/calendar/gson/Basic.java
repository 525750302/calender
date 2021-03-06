package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.gson;

import com.google.gson.annotations.SerializedName;

public class Basic {
    @SerializedName("city")   //JSON字段和Java字段建立映射
    public String cityName;   //城市名
    @SerializedName("id")
    public String weather_id;  //天气情况
    public Update update = new Update();
    public class Update {
        @SerializedName("loc")
        public String updateTime;   //天气更新时间
    }
}

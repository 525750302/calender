package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    public String data="";
    @SerializedName("tmp")
    public Temperature temperature = new Temperature();
    @SerializedName("cond")
    public More more = new More();
    public class Temperature {
        public  String max;
        public String min;
    }
    public class More {
        @SerializedName("txt_d")
        public String info;
    }
}

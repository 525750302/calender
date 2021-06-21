package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    @SerializedName("tmp")
    public String tempeture;
    @SerializedName("cond")
    public More more = new More();
    public class More {
        @SerializedName("txt")
        public String info;
    }
}

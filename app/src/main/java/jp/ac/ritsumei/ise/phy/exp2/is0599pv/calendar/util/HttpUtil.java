package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOKHttpRequest(String adress, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(adress).build();
        client.newCall(request).enqueue(callback);
    }
}


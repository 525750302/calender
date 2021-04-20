package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import android.app.Application;

public class application_calendar extends Application {
    String b;
    public String getB(){
        return this.b;
    }

    public void setB(String c){
        this.b = c;
    }

    @Override
    public void onCreate(){
        b = "monster";
        super.onCreate();
    }
}

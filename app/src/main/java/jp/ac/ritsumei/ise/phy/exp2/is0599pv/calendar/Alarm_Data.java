package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import android.app.Application;

import java.util.Calendar;

public class Alarm_Data extends Application {
    private String timeLable = "";
    private  long time = 0;
    private Calendar date;
    private int exist=0;

    public void get_Date(long time){
        this.time = time;

        date = Calendar.getInstance();
        date.setTimeInMillis(time);
        timeLable = String.format("%02d月%02d日 %02d:%02d",
                date.get(Calendar.MONTH)+1,
                date.get(Calendar.DAY_OF_MONTH),
                date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE));
    }
    public void setTime(long time){
        this.time = time;
    }
    public long getTime(){
        return time;
    }
    public void setTimeLable(String timeLable){
        this.timeLable = timeLable;
    }
    public String getTimeLable(){
        return timeLable;
    }

    @Override
    public String toString() {
        return getTimeLable();
    }

    public int getId(){
        return (int)(getTime()/1000/60);
    }

    public int getExist(){
        return (int)this.exist;
    }

    public void setExist(int a){
        this.exist = a;
    }

    @Override
    public void onCreate() {
        timeLable = "";
        date.setTimeInMillis(System.currentTimeMillis());
        time = 0;
        exist = 0;
        super.onCreate( );
    }
}

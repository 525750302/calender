package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class Alarmweek extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar c = Calendar.getInstance();
        int day_inc = (2-c.get(Calendar.DAY_OF_WEEK)+7)%7;

        c.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000*day_inc);
        c.set(Calendar.HOUR_OF_DAY, 2);
        c.set(Calendar.MINUTE, 0);
        MainActivity.alarmManager.set(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(), PendingIntent.getBroadcast(context.getApplicationContext( ),
                        (int)(c.getTimeInMillis()/1000/60),new Intent(context.getApplicationContext( ),Alarmweek.class),0));
    }
}

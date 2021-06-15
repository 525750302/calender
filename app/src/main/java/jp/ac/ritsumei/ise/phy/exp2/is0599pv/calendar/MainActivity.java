package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity{

    private TextView tvTime;
    private Timer timer;
    //private Handler mHandler;
    private Calendar mCalendar;
    private application_calendar data;
    private int week = 0;
    public static AlarmManager alarmManager;
    private ListView lvAlarmList;
    private static final String KEY_ALARM_LIST = "alarmList";
    private AlarmData[] list= new AlarmData[5];

    private TextView[][] schedule_text = new TextView[5][5];
    private Switch edit_switch;

    private int[] edit_num = new int[2];

    protected void ini() {
        alarmManager = (AlarmManager)MainActivity.this.getSystemService(Context.ALARM_SERVICE);
        data = (application_calendar) getApplication( );
        edit_switch = findViewById(R.id.edit_switch);
        schedule_text[0][0] = findViewById(R.id.textView1_1);
        schedule_text[0][1] = findViewById(R.id.textView1_2);
        schedule_text[0][2] = findViewById(R.id.textView1_3);
        schedule_text[0][3] = findViewById(R.id.textView1_4);
        schedule_text[0][4] = findViewById(R.id.textView1_5);
        schedule_text[1][0] = findViewById(R.id.textView2_1);
        schedule_text[1][1] = findViewById(R.id.textView2_2);
        schedule_text[1][2] = findViewById(R.id.textView2_3);
        schedule_text[1][3] = findViewById(R.id.textView2_4);
        schedule_text[1][4] = findViewById(R.id.textView2_5);
        schedule_text[2][0] = findViewById(R.id.textView3_1);
        schedule_text[2][1] = findViewById(R.id.textView3_2);
        schedule_text[2][2] = findViewById(R.id.textView3_3);
        schedule_text[2][3] = findViewById(R.id.textView3_4);
        schedule_text[2][4] = findViewById(R.id.textView3_5);
        schedule_text[3][0] = findViewById(R.id.textView4_1);
        schedule_text[3][1] = findViewById(R.id.textView4_2);
        schedule_text[3][2] = findViewById(R.id.textView4_3);
        schedule_text[3][3] = findViewById(R.id.textView4_4);
        schedule_text[3][4] = findViewById(R.id.textView4_5);
        schedule_text[4][0] = findViewById(R.id.textView5_1);
        schedule_text[4][1] = findViewById(R.id.textView5_2);
        schedule_text[4][2] = findViewById(R.id.textView5_3);
        schedule_text[4][3] = findViewById(R.id.textView5_4);
        schedule_text[4][4] = findViewById(R.id.textView5_5);

        for (int day = 0; day < 5; ++day) {
            for (int time = 0; time < 5; ++time) {
                if (data.get_exist(day, time) == 1) {
                    String show_lesson;
                    show_lesson = "";
                    show_lesson = show_lesson + data.getName(day, time).toString( ) + "\n";
                    show_lesson = show_lesson + data.getPlace(day, time).toString( ) + "\n";
                    schedule_text[day][time].setText(show_lesson);
                    Drawable drawable = getResources( ).getDrawable(R.drawable.textview_border);
                    ;
                    if (data.get_online(day, time, week) == -1)
                        drawable = getResources( ).getDrawable(R.drawable.textview_border_offline);
                    else if (data.get_online(day, time, week) == 1)
                        drawable = getResources( ).getDrawable(R.drawable.textview_border_online);
                    else if (data.get_online(day, time, week) == 2)
                        drawable = getResources( ).getDrawable(R.drawable.textview_border_mix);
                    schedule_text[day][time].setBackground(drawable);
                }
            }
        }
        set_alarm();
        set_auto_time_check();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ini( );

        timer = new Timer( );//创建timer对象
        tvTime = findViewById(R.id.tvtime);

        timer.schedule(new TimerTask( ) {
            public void run() {
                Log.v("Timer", "run()...");
                mCalendar = Calendar.getInstance( );
                int mMonth = mCalendar.get(Calendar.MONTH) + 1;        //获取日期的月
                int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);      //获取日期的天
                int mWay = mCalendar.get(Calendar.DAY_OF_WEEK);      //获取日期的星期
                int hour = mCalendar.get(Calendar.HOUR_OF_DAY);//HOUR    进制为12小时   HOUR_OF_DAY  为24小时
                int minute = mCalendar.get(Calendar.MINUTE);//分钟
                int second = mCalendar.get(Calendar.SECOND) + 1;//秒数
                if (second == 60) {
                    minute += 1;
                    second = 0;
                }
                if (minute == 60) {
                    hour += 1;
                    minute = 0;
                }
                if (hour == 12) {
                    hour = 0;
                }
                String time_text = "";
                if (mWay == 2)
                    time_text = "月";
                if (mWay == 3)
                    time_text = "火";
                if (mWay == 4)
                    time_text = "水";
                if (mWay == 5)
                    time_text = "木";
                if (mWay == 6)
                    time_text = "金";
                if (mWay == 7)
                    time_text = "土";
                if (mWay == 1)
                    time_text = "日";

                int start_mon = 0;
                int start_day = 0;
                int pass_day = 0;
                int mon_max_data = 0;
                if (data.getSemester( ) == 1) {
                    start_day = 1;
                    start_mon = 4;
                } else {
                    start_day = 26;
                    start_mon = 9;
                }
                if (mMonth != start_mon) {
                    for (int i = start_mon; i != mMonth; i = i % 12 + 1) {
                        if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
                            mon_max_data = 31;
                        } else if (i == 4 || i == 6 || i == 9 || i == 11) {
                            mon_max_data = 30;
                        } else if (i == 2) {
                            mon_max_data = 28;
                        }

                        if (i != start_mon && i != mMonth) {
                            pass_day += mon_max_data;
                        } else if (i == start_mon) {
                            pass_day += mon_max_data - start_day;
                        }
                    }
                    pass_day += mDay;
                } else {
                    pass_day = mDay - start_day - 1;
                }

                week = (pass_day + mWay) / 7;

                String time = String.format("第%d週 %s曜日 %d:%02d:%02d", week + 1, time_text, hour, minute, second);

                mCalendar.set(Calendar.SECOND, second);
                mCalendar.set(Calendar.MINUTE, minute);
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);

                Message message = new Message( );
                message.what = 0;
                message.obj = time;
                mHandler.sendMessage(message);
            }
        }, 0, 1000);
    }

    Handler mHandler = new Handler( ) {
        @Override
        public void handleMessage(Message msg) {
            Log.v("Timer", "handleMessage()..");
            super.handleMessage(msg);
            String str = (String) msg.obj;
            tvTime.setText(str);
        }
    };

    public void on_click_1_1(View v) {
        edit_num[0] = 0;
        edit_num[1] = 0;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_1_2(View v) {
        edit_num[0] = 0;
        edit_num[1] = 1;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_1_3(View v) {
        edit_num[0] = 0;
        edit_num[1] = 2;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_1_4(View v) {
        edit_num[0] = 0;
        edit_num[1] = 3;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_1_5(View v) {
        edit_num[0] = 0;
        edit_num[1] = 4;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_2_1(View v) {
        edit_num[0] = 1;
        edit_num[1] = 0;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_2_2(View v) {
        edit_num[0] = 1;
        edit_num[1] = 1;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_2_3(View v) {
        edit_num[0] = 1;
        edit_num[1] = 2;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_2_4(View v) {
        edit_num[0] = 1;
        edit_num[1] = 3;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_2_5(View v) {
        edit_num[0] = 1;
        edit_num[1] = 4;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_3_1(View v) {
        edit_num[0] = 2;
        edit_num[1] = 0;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_3_2(View v) {
        edit_num[0] = 2;
        edit_num[1] = 1;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_3_3(View v) {
        edit_num[0] = 2;
        edit_num[1] = 2;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_3_4(View v) {
        edit_num[0] = 2;
        edit_num[1] = 3;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_3_5(View v) {
        edit_num[0] = 2;
        edit_num[1] = 4;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_4_1(View v) {
        edit_num[0] = 3;
        edit_num[1] = 0;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_4_2(View v) {
        edit_num[0] = 3;
        edit_num[1] = 1;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_4_3(View v) {
        edit_num[0] = 3;
        edit_num[1] = 2;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_4_4(View v) {
        edit_num[0] = 3;
        edit_num[1] = 3;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_4_5(View v) {
        edit_num[0] = 3;
        edit_num[1] = 4;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_5_1(View v) {
        edit_num[0] = 4;
        edit_num[1] = 0;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_5_2(View v) {
        edit_num[0] = 4;
        edit_num[1] = 1;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_5_3(View v) {
        edit_num[0] = 4;
        edit_num[1] = 2;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_5_4(View v) {
        edit_num[0] = 4;
        edit_num[1] = 3;
        transmit(edit_num[0], edit_num[1]);
    }

    public void on_click_5_5(View v) {
        edit_num[0] = 4;
        edit_num[1] = 4;
        transmit(edit_num[0], edit_num[1]);
    }

    public void Setting_button(View v){
        Intent intent=new Intent(MainActivity.this,setting.class);//把数据传递到NextActivity
        startActivity(intent);//启动activity
    }

    public void transmit(int day,int time){
        if(edit_switch.isChecked())
        {
            Intent intent=new Intent(MainActivity.this,edit_schedule.class);//把数据传递到NextActivity

            intent.putExtra("day", day);
            intent.putExtra("time", time);
            startActivity(intent);//启动activity
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();//关闭timer
        }
    }

    private void set_alarm()
    {
        for(int i =0;i<5;++i)
        {
            deleteAlarm(i);
        }
        for (int i =0;i<5;++i){
            if(data.get_exist(i,0)==1)
            {
                addAlarm(i,0,data.get_online(i, 0, week));
            }
            else if(data.get_exist(i,1)==1)
            {
                addAlarm(i,1,data.get_online(i, 1, week));
            }
        }
    }


    private void deleteAlarm(int day){
        if(list[day].getExist()==1) {
            alarmManager.cancel(PendingIntent.getBroadcast(MainActivity.this,list[day].getId(),new Intent(MainActivity.this,AlarmReceiver.class),0));
            list[day].setExist(0);
        }
    }

    public void addAlarm(int day,int class_no,int online){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int now_day_ofweek = c.get(Calendar.DAY_OF_WEEK);
        int day_inc = (day+1+7-now_day_ofweek)%7;
        c.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000*day_inc);
        if(class_no == 0 && online==-1) {
            c.set(Calendar.HOUR_OF_DAY, 9 - (int)(data.offline_time / 60));
            c.set(Calendar.MINUTE, 60 - (data.offline_time % 60));
        }
        else if(class_no == 0 && online==1){
            c.set(Calendar.HOUR_OF_DAY, 9 - (int)(data.online_time / 60));
            c.set(Calendar.MINUTE, 60 - (data.online_time % 60));
        }
        else if(class_no==1&& online==-1)
        {
            int i=0;
            if(data.in_advance>45)
                i = 1;
            c.set(Calendar.HOUR_OF_DAY, 10 - (int)((data.offline_time-45) / 60)-i);
            c.set(Calendar.MINUTE, (45 +60- (data.offline_time % 60))%60);
        }
        else if(class_no == 1 && online==1){
            int i=0;
            if(data.in_advance>45)
                i = 1;
            c.set(Calendar.HOUR_OF_DAY, 10 - (int)((data.online_time-45) / 60)-i);
            c.set(Calendar.MINUTE, (45 +60- (data.online_time % 60))%60);
        }
        list[day].get_Date(c.getTimeInMillis());
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                list[day].getTime(),PendingIntent.getBroadcast(MainActivity.this,
                        list[day].getId(),new Intent(MainActivity.this,AlarmReceiver.class),0));
        list[day].setExist(1);
    }

    public void set_auto_time_check() {
        Calendar c = Calendar.getInstance();
        int day_inc = (2-c.get(Calendar.DAY_OF_WEEK)+7)%7;

        c.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000*day_inc);
        c.set(Calendar.HOUR_OF_DAY, 2);
        c.set(Calendar.MINUTE, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                c.getTimeInMillis(),PendingIntent.getBroadcast(MainActivity.this,
                        (int)(c.getTimeInMillis()/1000/60),new Intent(MainActivity.this,Alarmweek.class),0));
    }

    private static class AlarmData{
        private String timeLable = "";
        private  long time = 0;
        private Calendar date;
        private int exist;

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
        public AlarmData(String ad){
            this.timeLable = ad;
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
            return exist;
        }

        public void setExist(int a){
            exist = a;
        }
    }

}

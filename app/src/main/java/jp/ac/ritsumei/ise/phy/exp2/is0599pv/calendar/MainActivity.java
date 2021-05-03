package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tvTime;
    private Timer timer;
    //private Handler mHandler;
    private Calendar mCalendar;
    private application_calendar data;

    private TextView[][] schedule_text = new TextView[5][5];
    private Switch edit_switch;

    private int[] edit_num = new int[2];

    protected void ini()
    {
        data = (application_calendar)getApplication();
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

        for (int day=0;day<5;++day)
        {
            for(int time=0;time<5;++time)
            {
                if(data.get_exist(day,time)==1)
                {
                    String show_lesson;
                    show_lesson = "";
                    show_lesson = show_lesson + data.getName(day,time).toString()+"\n";
                    show_lesson = show_lesson + data.getPlace(day,time).toString()+"\n";
                    schedule_text[day][time].setText(show_lesson);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ini();

        timer = new Timer();//创建timer对象
        tvTime=findViewById(R.id.tvtime);

        timer.schedule(new TimerTask() {
            public void run() {
                Log.v("Timer","run()...");
                mCalendar = Calendar.getInstance();
                int hour = mCalendar.get(Calendar.HOUR_OF_DAY);//HOUR    进制为12小时   HOUR_OF_DAY  为24小时
                int minute = mCalendar.get(Calendar.MINUTE);//分钟
                int second = mCalendar.get(Calendar.SECOND) + 1;//秒数
                if (second == 60) {
                    minute += 1;
                    second = 0;
                }
                if (minute == 60){
                    hour += 1;
                    minute = 0;
                }
                if (hour == 12){
                    hour = 0;
                }
                String time = String.format("%d:%02d:%02d", hour, minute, second);
                mCalendar.set(Calendar.SECOND, second);
                mCalendar.set(Calendar.MINUTE, minute);
                mCalendar.set(Calendar.HOUR_OF_DAY, hour);

                Message message=new Message();
                message.what=0;
                message.obj=time;
                mHandler.sendMessage(message);

            }
        },0,1000);
    }
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.v("Timer","handleMessage()..");
            super.handleMessage(msg);
            String str=(String)msg.obj;
            tvTime.setText(str);
        }
    };

        public void on_click_1_1(View v){
            edit_num[0]=0;
            edit_num[1]=0;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_1_2(View v){
            edit_num[0]=0;
            edit_num[1]=1;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_1_3(View v){
            edit_num[0]=0;
            edit_num[1]=2;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_1_4(View v){
            edit_num[0]=0;
            edit_num[1]=3;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_1_5(View v){
            edit_num[0]=0;
            edit_num[1]=4;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_2_1(View v){
            edit_num[0]=1;
            edit_num[1]=0;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_2_2(View v){
            edit_num[0]=1;
            edit_num[1]=1;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_2_3(View v){
            edit_num[0]=1;
            edit_num[1]=2;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_2_4(View v){
            edit_num[0]=1;
            edit_num[1]=3;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_2_5(View v){
            edit_num[0]=1;
            edit_num[1]=4;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_3_1(View v){
            edit_num[0]=2;
            edit_num[1]=0;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_3_2(View v){
            edit_num[0]=2;
            edit_num[1]=1;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_3_3(View v){
            edit_num[0]=2;
            edit_num[1]=2;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_3_4(View v){
            edit_num[0]=2;
            edit_num[1]=3;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_3_5(View v){
            edit_num[0]=2;
            edit_num[1]=4;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_4_1(View v){
            edit_num[0]=3;
            edit_num[1]=0;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_4_2(View v){
            edit_num[0]=3;
            edit_num[1]=1;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_4_3(View v){
            edit_num[0]=3;
            edit_num[1]=2;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_4_4(View v){
            edit_num[0]=3;
            edit_num[1]=3;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_4_5(View v){
            edit_num[0]=3;
            edit_num[1]=4;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_5_1(View v){
            edit_num[0]=4;
            edit_num[1]=0;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_5_2(View v){
            edit_num[0]=4;
            edit_num[1]=1;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_5_3(View v){
            edit_num[0]=4;
            edit_num[1]=2;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_5_4(View v){
            edit_num[0]=4;
            edit_num[1]=3;
            transmit(edit_num[0],edit_num[1]);
        }
        public void on_click_5_5(View v){
            edit_num[0]=4;
            edit_num[1]=4;
            transmit(edit_num[0],edit_num[1]);
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
}

package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView tvTime;
    private Timer timer;
    //private Handler mHandler;
    private Calendar mCalendar;
    private application_calendar data = (application_calendar)getApplicationContext();

    private TextView[][] schedule_text = new TextView[5][5];

    protected void ini()
    {
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
                schedule_text[day][time].setText("");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();//创建timer对象
        tvTime=findViewById(R.id.tvtime);
        ini();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer!=null){
            timer.cancel();//关闭timer
        }
    }
}

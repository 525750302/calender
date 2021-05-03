package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class edit_schedule extends AppCompatActivity {
    private static final String NONE = "";
    private int day;
    private int time;
    private application_calendar data;
    private TextView title;
    private EditText edit_name;
    private TextView edit_time;
    private EditText edit_place;
    private EditText edit_remark;
    private CheckBox[] online_week = new CheckBox[15];
    private CheckBox[] offline_week = new CheckBox[15];

    private void ini(){
        data = (application_calendar)getApplication();
        title = findViewById(R.id.textView_title);

        edit_name = findViewById(R.id.edit_name);
        edit_time = findViewById(R.id.edit_time);
        edit_place = findViewById(R.id.edit_place);
        edit_remark = findViewById(R.id.edit_remark);

        online_week[0] = findViewById(R.id.checkBox);
        online_week[1] = findViewById(R.id.checkBox2);
        online_week[2] = findViewById(R.id.checkBox3);
        online_week[3] = findViewById(R.id.checkBox4);
        online_week[4] = findViewById(R.id.checkBox5);
        online_week[5] = findViewById(R.id.checkBox6);
        online_week[6] = findViewById(R.id.checkBox7);
        online_week[7] = findViewById(R.id.checkBox8);
        online_week[8] = findViewById(R.id.checkBox9);
        online_week[9] = findViewById(R.id.checkBox10);
        online_week[10] = findViewById(R.id.checkBox11);
        online_week[11] = findViewById(R.id.checkBox12);
        online_week[12] = findViewById(R.id.checkBox13);
        online_week[13] = findViewById(R.id.checkBox14);
        online_week[14] = findViewById(R.id.checkBox15);

        offline_week[0] = findViewById(R.id.checkBox2_1);
        offline_week[1] = findViewById(R.id.checkBox2_2);
        offline_week[2] = findViewById(R.id.checkBox2_3);
        offline_week[3] = findViewById(R.id.checkBox2_4);
        offline_week[4] = findViewById(R.id.checkBox2_5);
        offline_week[5] = findViewById(R.id.checkBox2_6);
        offline_week[6] = findViewById(R.id.checkBox2_7);
        offline_week[7] = findViewById(R.id.checkBox2_8);
        offline_week[8] = findViewById(R.id.checkBox2_9);
        offline_week[9] = findViewById(R.id.checkBox2_10);
        offline_week[10] = findViewById(R.id.checkBox2_11);
        offline_week[11] = findViewById(R.id.checkBox2_12);
        offline_week[12] = findViewById(R.id.checkBox2_13);
        offline_week[13] = findViewById(R.id.checkBox2_14);
        offline_week[14] = findViewById(R.id.checkBox2_15);

        if(day != -1 && time != -1){
            if(data.get_exist(day,time)==1){
                edit_name.setText(data.getName(day,time));
                String time_text="";
                if(day == 0)
                    time_text = "月";
                if(day == 1)
                    time_text = "火";
                if(day == 2)
                    time_text = "水";
                if(day == 3)
                    time_text = "木";
                if(day == 4)
                    time_text = "金";
                edit_time.setText(time_text+"曜日"+time+"時限");

                edit_place.setText(data.getPlace(day,time));
                edit_remark .setText(data.getRemark(day,time));

                for (int i=0;i<15;++i)
                {
                    if(data.get_online(day,time,i)==1)
                    {
                        online_week[i].setChecked(true);
                        offline_week[i].setChecked(false);
                    }
                    else if(data.get_online(day,time,i)==-1)
                    {
                        offline_week[i].setChecked(true);
                        online_week[i].setChecked(false);
                    }
                    else if(data.get_online(day,time,i)==2)
                    {
                        offline_week[i].setChecked(true);
                        online_week[i].setChecked(true);
                    }
                    else
                    {
                        offline_week[i].setChecked(false);
                        online_week[i].setChecked(false);
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        day = intent.getIntExtra("day",-1);
        time = intent.getIntExtra("time",-1);

        setContentView(R.layout.activity_edit_schedule);
        ini();
    }

    public void button_clear(View v){
        edit_name.setText("");
        edit_place.setText("");
        edit_remark .setText("");
        for(int i=0;i<15;++i){
            offline_week[i].setChecked(false);
            online_week[i].setChecked(false);
        }
    }

    public void button_yes(View v)
    {
        if(edit_name.getText().toString()==NONE){
            data.set_exist(day,time,0);
        }
        else{
            data.setName(edit_name.getText().toString(),day,time);
            data.setPlace(edit_place.getText().toString(),day,time);
            data.setRemark(edit_remark.getText().toString(),day,time);
            int[] week = new int[15];
            for(int i =1;i<15;++i)
            {
                if(online_week[i].isChecked()==true && offline_week[i].isChecked()==true)
                {
                    week[i]=2;
                }
                else if(online_week[i].isChecked()==false && offline_week[i].isChecked()==true)
                {
                    week[i]=-1;
                }
                else if(offline_week[i].isChecked()==false && online_week[i].isChecked()==true)
                {
                    week[i]=1;
                }
                else if(offline_week[i].isChecked()==false && online_week[i].isChecked()==false)
                {
                    week[i]=0;
                }
            }
            data.change_online(day,time,week);
            data.set_exist(day,time,1);

            Intent intent=new Intent(edit_schedule.this,MainActivity.class);//把数据传递到NextActivity
            startActivity(intent);//启动activity
        }
    }

    public void button_no(View v)
    {
        Intent intent=new Intent(edit_schedule.this,MainActivity.class);//把数据传递到NextActivity
        startActivity(intent);//启动activity
    }
}
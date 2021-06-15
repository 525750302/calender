package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class setting extends AppCompatActivity {
    private CheckBox set_alarm;
    private CheckBox in_advance;
    private CheckBox aki;
    private CheckBox haru;
    private EditText online_time;
    private EditText offline_time;
    private application_calendar data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        data = (application_calendar) getApplication( );
        ini();
    }

    private  void ini(){
        set_alarm = findViewById(R.id.checkBox_alarm);
        in_advance = findViewById(R.id.checkBox17);
        aki = findViewById(R.id.checkBox_aki);
        haru = findViewById(R.id.checkBox_haru);
        online_time = findViewById(R.id.editTextTextPersonName2);
        offline_time = findViewById(R.id.editTextTextPersonName4);

        if(data.getSemester()==1)
        {
            aki.setChecked(false);
            haru.setChecked(true);
        }
        else
        {
            aki.setChecked(true);
            haru.setChecked(false);
        }
        if(data.getSet_alarm()==1)
        {
            set_alarm.setChecked(true);
        }
        else
        {
            set_alarm.setChecked(false);
        }
        if(data.getIn_advance()==1)
        {
            in_advance.setChecked(true);
        }
        else
        {
            in_advance.setChecked(false);
        }
        offline_time.setText(Integer.toString(data.getOffline_time()));
        online_time.setText(Integer.toString(data.getOnline_time()));
    }


    public void haru(View v){
        if(haru.isChecked() == true)
            aki.setChecked(false);
        data.setSemester(1);
    }

    public void aki(View v){
        if(aki.isChecked() == true)
            haru.setChecked(false);
        data.setSemester(0);
    }

    public void button_maru(View v){
        if(set_alarm.isChecked())
        {
            data.setSet_alarm(1);
        }
        else
        {
            data.setSet_alarm(0);
        }

        if(in_advance.isChecked()){
            data.setIn_advance(1);
        }
        else {
            data.setIn_advance(0);
        }

        if(haru.isChecked()){
            data.setSemester(1);
        }
        else
        {
            data.setSemester(0);
        }

        if(aki.isChecked()){
            data.setSemester(0);
        }
        else
        {
            data.setSemester(1);
        }

        int online_time_num = Integer.valueOf(online_time.getText().toString());
        int offline_time_num = Integer.valueOf(offline_time.getText().toString());
        
        data.setOnline_time(online_time_num);
        data.setOffline_time(offline_time_num);
        Intent intent=new Intent(setting.this,MainActivity.class);//把数据传递到NextActivity
        startActivity(intent);//启动activity
    }

    public void button_no(View v)
    {
        Intent intent=new Intent(setting.this,MainActivity.class);//把数据传递到NextActivity
        startActivity(intent);//启动activity
    }
}
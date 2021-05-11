package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import android.app.Application;

public class application_calendar extends Application{
    String[][] name= new String[5][5];
    String[][] place= new String[5][5];
    String[][] remark= new String[5][5];
    int[][][] online = new int[5][5][15];
    int[][] exist = new int[5][5];
    int semester = 1;

    public String getName(int day,int time)
    {
        return name[day][time];
    }
    public String getPlace(int day,int time)
    {
        return place[day][time];
    }
    public String getRemark(int day,int time)
    {
        return remark[day][time];
    }
    public void setName(String aname,int day,int time)
    {
        name[day][time] = aname;
    }
    public void setPlace(String aplace,int day,int time) { place[day][time] = aplace; }
    public void setRemark(String aremark,int day,int time)
    {
        remark[day][time] = aremark;
    }
    public int get_online(int day,int time,int week)
    {
        return online[day][time][week];
    }
    public int getSemester(){return semester;}
    public void setSemester(int asemester){semester = asemester;}

    public void change(int day,int time,String aplace,int aexist)
    {
        place[day][time] = aplace;
        exist[day][time] = aexist;
    }
    public void change_online(int day,int time,int[] aonline)
    {
        for(int week = 0;week<15;++week)
            online[day][time][week] = aonline[week];
    }

    public int get_exist(int day,int time)
    {
        return exist[day][time];
    }
    public void set_exist(int day,int time,int num)
    {
        exist[day][time]=num;
    }

    @Override
    public void onCreate(){
        for(int day = 0;day<5;++day)
        {
            for(int time = 0;time<5;++time)
            {
                name[day][time] = "";
                place[day][time] = "";
                for(int week = 0;week<14;++week)
                    online[day][time][week] = 0;
                exist[day][time] = 0;
            }
        }
        super.onCreate();
    }
}

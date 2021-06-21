package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qweather.sdk.bean.air.AirNowBean;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherDailyBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.io.IOException;

import jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.gson.Forecast;
import jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.gson.Weather;
import jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.util.HttpUtil;
import jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar.util.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class WeatherActivity extends AppCompatActivity {

    private ScrollView weatherLayout;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;  //气温

    private TextView weatherInfoText;  //天气概况

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private Weather weather = new Weather();
    Forecast forecastDaily = new Forecast();

    String location = "BDA09";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        HeConfig.init("HE2106201859301927",
                "e3a39a8f61ec414eadac9c9a2a1941bc");
        HeConfig.switchToDevService();

        requestWeather();
    }


    /*根据天气ID请求天气信息*/
    public void requestWeather() {
        QWeather.getWeatherNow(this, location, Lang.JA, Unit.METRIC, new QWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK == weatherBean.getCode()) {
                    WeatherNowBean.NowBaseBean now = weatherBean.getNow();
                    weather.now.tempeture= now.getTemp();
                    weather.basic.update.updateTime=now.getObsTime();
                    weather.now.more.info = now.getText();
                    requestWeather_daily();
                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
    }

    public void requestWeather_daily() {
        QWeather.getWeather7D(this, location, Lang.JA, Unit.METRIC, new QWeather.OnResultWeatherDailyListener( ) {
            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "getWeather onError: " + e);
            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                int i = 0;
                Log.i(TAG, "getWeather onSuccess: " + new Gson( ).toJson(weatherDailyBean));
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK == weatherDailyBean.getCode( )) {
                    for (WeatherDailyBean.DailyBean dailyBean : weatherDailyBean.getDaily( )) {
                        forecastDaily = new Forecast( );
                        forecastDaily.data = dailyBean.getFxDate( );
                        forecastDaily.more.info = dailyBean.getTextDay( );
                        forecastDaily.temperature.max = dailyBean.getTempMax( );
                        forecastDaily.temperature.min = dailyBean.getTempMin( );
                        weather.forecastList[i] = forecastDaily;
                        i++;
                    }
                    System.out.println( "22222222222" );
                    showWeatherInfo();
                    //requestWeather_AQI();
                } else {
                    //在此查看返回数据失败的原因
                    Code code = weatherDailyBean.getCode( );
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
    }
    public void requestWeather_AQI() {
        QWeather.getAirNow(this, location, Lang.JA, new QWeather.OnResultAirNowListener(){

            @Override
            public void onError(Throwable throwable) {
                Log.i(TAG, "getWeather onError: " + throwable);
            }

            @Override
            public void onSuccess(AirNowBean airNowBean) {
                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(airNowBean));
                if (Code.OK == airNowBean.getCode()) {
                    AirNowBean.NowBean city_AQI = airNowBean.getNow();
                    weather.aqi.city.aqi = city_AQI.getAqi();
                    weather.aqi.city.pm25 = city_AQI.getPm2p5();
                } else {
                    //在此查看返回数据失败的原因
                    Code code = airNowBean.getCode();
                    Log.i(TAG, "failed code: " + code);
                }
            }
        });
    }

    //缓存数据下处理并展示Weather实体类中的数据
    private void showWeatherInfo() {
        String updateTime = weather.basic.update.updateTime; //split：分解
        String degree = weather.now.tempeture+"°C";
        String weatherInfo = weather.now.more.info;
        titleCity.setText("京都 ");
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        for (int i =0; i <7;++i){
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,forecastLayout,false);
            TextView dateText = (TextView)view.findViewById(R.id.data_text);
            TextView infoText = (TextView)view.findViewById(R.id.info_text);
            TextView maxText = (TextView)view.findViewById(R.id.max_text);
            TextView minText = (TextView)view.findViewById(R.id.min_text);
            dateText.setText(weather.forecastList[i].data);
            infoText.setText(weather.forecastList[i].more.info);
            maxText.setText("max:"+weather.forecastList[i].temperature.max+"°C");
            minText.setText("min:"+weather.forecastList[i].temperature.min+"°C");
            forecastLayout.addView(view);
        }
        //if (weather.aqi != null){
            //aqiText.setText(weather.aqi.city.aqi);
            //pm25Text.setText(weather.aqi.city.pm25);
        //}
        weatherLayout.setVisibility(View.VISIBLE);
    }

    public void but_exit(View view){
        finish();
    }

    //初识化控件
    private void initView() {
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);
        degreeText = (TextView)findViewById(R.id.degree_text);
        weatherInfoText = (TextView)findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);
        aqiText = (TextView)findViewById(R.id.api_text);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView)findViewById(R.id.sport_text);
    }
}

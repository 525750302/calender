package jp.ac.ritsumei.ise.phy.exp2.is0599pv.calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayAlarm extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button close_btn;
    private TextView font_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_alarm);
        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle("wake up！");
        builder.setContentText("wake up！");
        builder.setSmallIcon(android.R.drawable.ic_lock_idle_alarm);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

        notificationManager.notify(0x101,notification);
        notificationManager.cancel(0);

        font_txt = (TextView)findViewById(R.id.font_content) ;
        font_txt.setText("wake up!!!");

        close_btn = (Button)findViewById(R.id.close_btn);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                finish();
            }
        });
    }



    @Override

    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
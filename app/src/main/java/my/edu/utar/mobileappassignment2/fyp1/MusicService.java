package my.edu.utar.mobileappassignment2.fyp1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


import androidx.annotation.Nullable;

//https://www.youtube.com/watch?v=p2ffzsCqrs8
public class MusicService extends Service {
    private MediaPlayer player;
    private static boolean isRunning;
    private static boolean isCheck;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Music background
        //https://www.chosic.com/download-audio/29782/
        player = MediaPlayer.create(this, R.raw.happybgm);
        player.setLooping(true);
        player.start();
        player.setVolume(0.5f, 0.5f);
        //let main check
        isRunning = true;
        //let button check
        isCheck = true;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //let main check
        isRunning = true;
        //let button check
        isCheck = false;
        player.stop();
    }

    public static boolean isRunning() {
        return isRunning;
    }

    public static boolean isCheck() {
        return isCheck;
    }
}

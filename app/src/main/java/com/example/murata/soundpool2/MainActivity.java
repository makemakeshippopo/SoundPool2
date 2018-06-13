package com.example.murata.soundpool2;

import android.media.AudioAttributes;
import android.media.SoundPool;

///
import android.os.Handler;
///
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int soundOne, soundTwo;
    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて
                .setMaxStreams(2)
                .build();

        // one.wav をロードしておく
        soundOne = soundPool.load(this, R.raw.hb_c, 1);

        // load が終わったか確認する場合
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.d("debug","sampleId="+sampleId);
                Log.d("debug","status="+status);
            }
        });

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////
                final Handler handler = new Handler();
                final Runnable r = new Runnable() {
                    int count = 0;
                    @Override
                    public void run() {
                        // UIスレッド
                        count++;
                        if (count > 1000) { //1000回実行したら終了
                            return;
                        }
                        soundPool.play(soundOne, 1.0f, 1.0f, 1, 0, 1);

                        //doSomething(); // 何かやる
                        handler.postDelayed(this, 1000);
                    }
                };
                handler.post(r);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // two.wav の再生
                soundPool.play(soundOne, 1.0f, 1.0f, 1, 0, 1);

            }
        });
    }
}

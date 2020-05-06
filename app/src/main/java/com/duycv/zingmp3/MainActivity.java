package com.duycv.zingmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button playBtn;
    Button previousBtn;
    Button nextBtn;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (Button) findViewById(R.id.button_play);
        previousBtn = (Button) findViewById(R.id.button_previous);
        nextBtn = (Button) findViewById(R.id.button_next);

        mp = MediaPlayer.create(this, R.raw.music);
    }

    public void playBtnClick(View view) {
        if(mp.isPlaying()) {
            mp.start();
            playBtn.setBackgroundResource(R.drawable.play);
        } else {
            mp.stop();
            playBtn.setBackgroundResource(R.drawable.pause);
        }
    }
}

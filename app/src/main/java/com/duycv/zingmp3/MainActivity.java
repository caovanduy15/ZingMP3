package com.duycv.zingmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.SimpleTimeZone;

public class MainActivity extends AppCompatActivity {

    TextView titleTextView, timeLastTextView, totalTimeTextView;
    SeekBar seekBar;
    ImageButton next, previous, play, stop;
    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();
        AddSong();
        RenewMedia();
        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.pause);
                }
                SetTotalTime();
                UpdateCurrentTime();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > arraySong.size() - 1) {
                    position = 0;
                }
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                RenewMedia();
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
                SetTotalTime();
                UpdateCurrentTime();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0) {
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                RenewMedia();
                mediaPlayer.start();
                play.setImageResource(R.drawable.pause);
                SetTotalTime();
                UpdateCurrentTime();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // khi thay đổi
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // khi bắt đầu chạm
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // khi chạm xong, phát đúng thời gian seekBar kéo đến
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private  void Init() {
        timeLastTextView = (TextView) findViewById(R.id.lastTime);
        totalTimeTextView = (TextView) findViewById(R.id.totalTime);
        titleTextView = (TextView) findViewById(R.id.title);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        next = (ImageButton) findViewById(R.id.button_next);
        play = (ImageButton) findViewById(R.id.button_play);
        previous = (ImageButton) findViewById(R.id.button_previous);
    }

    private void AddSong() {
        arraySong = new ArrayList<Song>();
        arraySong.add((new Song("music", R.raw.music)));
        arraySong.add((new Song("Song gio", R.raw.songgio)));
        arraySong.add((new Song("Thuan theo y troi", R.raw.thuantheoytroi)));
    }

    private void RenewMedia() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        titleTextView.setText(arraySong.get(position).getTitle());
    }

    private void SetTotalTime(){
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        totalTimeTextView.setText(timeFormat.format(mediaPlayer.getDuration()));
        // cập nhật thời gian max của Song
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void UpdateCurrentTime(){
        final Handler handler = new Handler();

        // sau 0.1s tạo handler
        handler.postDelayed(new Runnable() {
            @Override

            // tạo Runable để lấy thời gian hiện tại
            public void run() {
                SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                timeLastTextView.setText(timeFormat.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                // vòng lặp sau 0.1s cập nhật tiếp
                handler.postDelayed(this,100);
            }
        }, 100);
    }
}

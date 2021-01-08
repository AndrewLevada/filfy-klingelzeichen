package com.example.filfyklingelzeichen;

import android.annotation.SuppressLint;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RingActivity extends AppCompatActivity {
    final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.default_music);

    Button check = findViewById(R.id.activity_ring_check);

    TextView equation_tv = findViewById(R.id.activity_ring_equation);
    TextView message_tv = findViewById(R.id.activity_ring_message);

    EditText root1_et = findViewById(R.id.activity_ring_root1);
    EditText root2_et = findViewById(R.id.activity_ring_root2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        Equation eq = new Equation(new Equation.Easy());
        int[] ans = eq.getAnswers();
        equation_tv.setText(eq.getEquation());

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int root1 = Integer.parseInt(root1_et.getText().toString());
                int root2 = Integer.parseInt(root2_et.getText().toString());

                if ((root1 == ans[0] && root2 == ans[1]) || (root1 == ans[1] && root2 == ans[0])) {
                    mediaPlayer.stop();
                    finish();
                } else {
                    message_tv.setText("Wrong answer");
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void playAlarmSound () {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @SuppressLint("StaticFieldLeak")
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.reset();
                            mediaPlayer.release();
                        }
                    });
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });

                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                    mediaPlayer.setVolume(1.0f, 1.0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.prepare();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        }.execute();
    }
}

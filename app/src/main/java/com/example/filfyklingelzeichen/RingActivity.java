package com.example.filfyklingelzeichen;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.filfyklingelzeichen.alarmmanager.AlarmService;

import java.util.ArrayList;

public class RingActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        Button check = findViewById(R.id.activity_ring_check);
        TextView equation_tv = findViewById(R.id.activity_ring_equation);
        EditText root1_et = findViewById(R.id.input_root1);
        EditText root2_et = findViewById(R.id.input_root2);

        Equation eq = new Equation(new Equation.Easy());
        int[] answers = eq.getAnswers();
        equation_tv.setText(eq.getEquation());

        Log.e("APP", eq.x1 + " " + eq.x2);

        check.setOnClickListener(v -> {
            ArrayList<Integer> roots = new ArrayList<>();
            roots.add(Integer.parseInt(root1_et.getText().toString()));
            roots.add(Integer.parseInt(root2_et.getText().toString()));

            boolean isCorrect = false;

            for (int a = 0; a < answers.length; a++) {
                isCorrect = false;
                for (int r = 0; r < roots.size(); r++) {
                    if (answers[a] == roots.get(r)) {
                        isCorrect = true;
                        break;
                    }
                }

                if (!isCorrect) break;
            }

            if (isCorrect) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            } else Toolbox.showSimpleDialog(this,
                    R.string.error, R.string.wrong_answer, R.string.try_more);
        });
    }
}

package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinalScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textViewCongratulations = findViewById(R.id.textViewCongratulations);
        TextView textViewScore = findViewById(R.id.textViewScore);
        Button buttonTakeNewQuiz = findViewById(R.id.buttonTakeNewQuiz);
        Button buttonFinish = findViewById(R.id.buttonFinish);

        String userName = getIntent().getStringExtra("userName");
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        textViewCongratulations.setText("Congratulations " + userName + "!");
        textViewScore.setText(score + " / " + total);

        buttonTakeNewQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(FinalScoreActivity.this, MainActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
            finish();
        });

        // Close app
        buttonFinish.setOnClickListener(v -> {
            finishAffinity();
        });
    }
}
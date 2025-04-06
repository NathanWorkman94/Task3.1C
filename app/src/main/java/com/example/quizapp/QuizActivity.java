package com.example.quizapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.graphics.Color;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ProgressBar;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView textViewGreeting;
    private TextView textViewProgress;
    private ProgressBar progressBar;
    private List<Question> questionList;
    private int currentQuestionIndex = 0; // To track questions
    private int selectedAnswerIndex = -1; // To track selected answer
    private String userName;
    private TextView textViewQuestion;
    private TextView textViewQuestionDetails;
    private Button buttonAnswer1;
    private Button buttonAnswer2;
    private Button buttonAnswer3;
    private Button buttonSubmit;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewGreeting = findViewById(R.id.textViewGreeting);
        userName = getIntent().getStringExtra("userName");

        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewQuestionDetails = findViewById(R.id.textViewQuestionDetails);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewProgress = findViewById(R.id.textViewProgress);
        progressBar = findViewById(R.id.progressBar);

        questionList = QuestionBank.getQuestions();

        loadQuestion();

        buttonAnswer1.setOnClickListener(v -> handleAnswerSelection(0));
        buttonAnswer2.setOnClickListener(v -> handleAnswerSelection(1));
        buttonAnswer3.setOnClickListener(v -> handleAnswerSelection(2));

        // Button logic to either submit answer or go to next question
        buttonSubmit.setOnClickListener(v -> {
            if (buttonSubmit.getText().equals("Submit")) {
                checkAnswer();
            } else {
                goToNextQuestion();
            }
        });
    }

    private void loadQuestion() {
        Question currentQuestion = questionList.get(currentQuestionIndex);

        // Show greeting on first question only
        if (currentQuestionIndex == 0 && userName != null) {
            textViewGreeting.setText("Welcome " + userName + "!");
        } else {
            textViewGreeting.setText("");
        }

        // Display question number and the question
        textViewQuestion.setText("Question " + (currentQuestionIndex + 1));
        textViewQuestionDetails.setText(currentQuestion.getQuestionText());

        // Load options for the buttons
        List<String> options = currentQuestion.getOptions();
        buttonAnswer1.setText(options.get(0));
        buttonAnswer2.setText(options.get(1));
        buttonAnswer3.setText(options.get(2));

        // Update progress and progress bar
        String progressText = (currentQuestionIndex + 1) + "/" + questionList.size();
        textViewProgress.setText(progressText);
        int progress = (int) (((double)(currentQuestionIndex) / questionList.size()) * 100);
        progressBar.setProgress(progress);
    }

    private void handleAnswerSelection(int index) {
        selectedAnswerIndex = index;

        // Reset colour of buttons
        buttonAnswer1.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonAnswer1.setTextColor(Color.parseColor("#000000"));
        buttonAnswer2.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonAnswer2.setTextColor(Color.parseColor("#000000"));
        buttonAnswer3.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonAnswer3.setTextColor(Color.parseColor("#000000"));

        // Set selected button to blue
        if (index == 0){
            buttonAnswer1.setBackgroundColor(Color.parseColor("#2d50c4"));
            buttonAnswer1.setTextColor(Color.parseColor("#ffffff"));
            buttonSubmit.setBackgroundColor(Color.parseColor("#2d50c4"));
            buttonSubmit.setTextColor(Color.parseColor("#ffffff"));
        }
        else if (index == 1){
            buttonAnswer2.setBackgroundColor(Color.parseColor("#2d50c4"));
            buttonAnswer2.setTextColor(Color.parseColor("#ffffff"));
            buttonSubmit.setBackgroundColor(Color.parseColor("#2d50c4"));
            buttonSubmit.setTextColor(Color.parseColor("#ffffff"));
        } else {
            buttonAnswer3.setBackgroundColor(Color.parseColor("#2d50c4"));
            buttonAnswer3.setTextColor(Color.parseColor("#ffffff"));
            buttonSubmit.setBackgroundColor(Color.parseColor("#2d50c4"));
            buttonSubmit.setTextColor(Color.parseColor("#ffffff"));
        }
    }

    private void checkAnswer() {
        // Handle no answer selected
        if (selectedAnswerIndex == -1) {
            Toast.makeText(this, "Don't forget to select an answer!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get current question and the answer
        Question currentQuestion = questionList.get(currentQuestionIndex);
        int correctAnswerIndex = currentQuestion.getCorrectAnswerIndex();

        // Handle scoring
        if (selectedAnswerIndex == correctAnswerIndex)
        {
            score++;
        }

        // Colour the correct answer in green
        if (correctAnswerIndex == 0) {
            buttonAnswer1.setBackgroundColor(Color.parseColor("#44c236"));
            buttonAnswer1.setTextColor(Color.parseColor("#ffffff"));
        } else if (correctAnswerIndex == 1) {
            buttonAnswer2.setBackgroundColor(Color.parseColor("#44c236"));
            buttonAnswer2.setTextColor(Color.parseColor("#ffffff"));
        } else {
            buttonAnswer3.setBackgroundColor(Color.parseColor("#44c236"));
            buttonAnswer3.setTextColor(Color.parseColor("#ffffff"));
        }

        // Colour the answer red if wrong
        if (selectedAnswerIndex != correctAnswerIndex) {
            if (selectedAnswerIndex == 0) {
                buttonAnswer1.setBackgroundColor(Color.parseColor("#cf4848"));
                buttonAnswer1.setTextColor(Color.parseColor("#ffffff"));
            } else if (selectedAnswerIndex == 1) {
                buttonAnswer2.setBackgroundColor(Color.parseColor("#cf4848"));
                buttonAnswer2.setTextColor(Color.parseColor("#ffffff"));
            } else {
                buttonAnswer3.setBackgroundColor(Color.parseColor("#cf4848"));
                buttonAnswer3.setTextColor(Color.parseColor("#ffffff"));
            }
        }
        buttonAnswer1.setEnabled(false);
        buttonAnswer2.setEnabled(false);
        buttonAnswer3.setEnabled(false);

        buttonSubmit.setText("Next");
    }

    private void goToNextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < questionList.size()) {
            loadQuestion();
            resetButtons();
            buttonSubmit.setText("Submit");
        } else {
            Intent intent = new Intent(QuizActivity.this, FinalScoreActivity.class);
            intent.putExtra("userName", getIntent().getStringExtra("userName"));
            intent.putExtra("score", score);
            intent.putExtra("total", questionList.size());
            startActivity(intent);
            finish();
        }
    }

    // Reset the appearance and functionality of the buttons
    private void resetButtons() {
        selectedAnswerIndex = -1;

        buttonAnswer1.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonAnswer1.setTextColor(Color.parseColor("#000000"));

        buttonAnswer2.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonAnswer2.setTextColor(Color.parseColor("#000000"));

        buttonAnswer3.setBackgroundColor(Color.parseColor("#ffffff"));
        buttonAnswer3.setTextColor(Color.parseColor("#000000"));

        buttonSubmit.setBackgroundColor(Color.parseColor("#cccccc"));
        buttonSubmit.setTextColor(Color.parseColor("#ffffff"));

        buttonAnswer1.setEnabled(true);
        buttonAnswer2.setEnabled(true);
        buttonAnswer3.setEnabled(true);

        buttonSubmit.setText("Submit");
    }
}
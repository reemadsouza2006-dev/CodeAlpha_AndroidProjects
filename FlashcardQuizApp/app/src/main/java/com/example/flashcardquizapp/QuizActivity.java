package com.example.flashcardquizapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    TextView tvQuizQuestion, tvScore;
    EditText etQuizAnswer;
    Button btnCheckAnswer, btnQuizNext;

    DatabaseHelper databaseHelper;
    Cursor cursor;

    int score = 0;
    boolean answerChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuizQuestion = findViewById(R.id.tvQuizQuestion);
        tvScore = findViewById(R.id.tvScore);
        etQuizAnswer = findViewById(R.id.etQuizAnswer);
        btnCheckAnswer = findViewById(R.id.btnCheckAnswer);
        btnQuizNext = findViewById(R.id.btnQuizNext);

        databaseHelper = new DatabaseHelper(this);

        cursor = databaseHelper.getReadableDatabase().rawQuery(
                "SELECT question, answer FROM flashcards",
                null
        );

        if (cursor.moveToFirst()) {
            showQuestion();
        } else {
            Toast.makeText(
                    this,
                    "No flashcards available",
                    Toast.LENGTH_SHORT
            ).show();
        }

        btnCheckAnswer.setOnClickListener(v -> checkAnswer());

        btnQuizNext.setOnClickListener(v -> nextQuestion());
    }

    private void showQuestion() {

        String question = cursor.getString(0);

        tvQuizQuestion.setText(question);
        etQuizAnswer.setText("");

        answerChecked = false;
    }

    private void checkAnswer() {

        if (answerChecked) {
            Toast.makeText(
                    this,
                    "Answer already checked",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String userAnswer = etQuizAnswer.getText()
                .toString()
                .trim();

        if (userAnswer.isEmpty()) {
            Toast.makeText(
                    this,
                    "Please enter an answer",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        String correctAnswer = cursor.getString(1);

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {

            score++;

            Toast.makeText(
                    this,
                    "Correct!",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Wrong answer",
                    Toast.LENGTH_SHORT
            ).show();
        }

        tvScore.setText("Score: " + score);

        answerChecked = true;
    }

    private void nextQuestion() {

        if (!answerChecked) {
            Toast.makeText(
                    this,
                    "Please check your answer first",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (cursor.moveToNext()) {

            showQuestion();

        } else {

            Toast.makeText(
                    this,
                    "Quiz completed! Final Score: " + score,
                    Toast.LENGTH_LONG
            ).show();

            btnCheckAnswer.setEnabled(false);
            btnQuizNext.setEnabled(false);
            etQuizAnswer.setEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cursor != null) {
            cursor.close();
        }
    }
}
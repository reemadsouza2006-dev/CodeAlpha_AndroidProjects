package com.example.fitnesstracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvSteps, tvWorkout, tvCalories;
    EditText etExercise, etMinutes, etCalories;
    Button btnAddActivity;

    FitnessDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvSteps = findViewById(R.id.tvSteps);
        tvWorkout = findViewById(R.id.tvWorkout);
        tvCalories = findViewById(R.id.tvCalories);

        etExercise = findViewById(R.id.etExercise);
        etMinutes = findViewById(R.id.etMinutes);
        etCalories = findViewById(R.id.etCalories);

        btnAddActivity = findViewById(R.id.btnAddActivity);

        databaseHelper = new FitnessDatabaseHelper(this);

        loadSavedData();

        btnAddActivity.setOnClickListener(v -> addActivity());
    }

    private void loadSavedData() {

        int totalSteps = databaseHelper.getTotalSteps();
        int totalMinutes = databaseHelper.getTotalMinutes();
        int totalCalories = databaseHelper.getTotalCalories();

        tvSteps.setText(
                getString(R.string.steps_format, totalSteps)
        );

        tvWorkout.setText(
                getString(R.string.workout_format, totalMinutes)
        );

        tvCalories.setText(
                getString(R.string.calories_format, totalCalories)
        );
    }

    private void addActivity() {

        String exercise = etExercise.getText().toString().trim();
        String minutesText = etMinutes.getText().toString().trim();
        String caloriesText = etCalories.getText().toString().trim();

        if (exercise.isEmpty()) {
            etExercise.setError(getString(R.string.enter_exercise));
            return;
        }

        if (minutesText.isEmpty()) {
            etMinutes.setError(getString(R.string.enter_minutes));
            return;
        }

        if (caloriesText.isEmpty()) {
            etCalories.setError(getString(R.string.enter_calories));
            return;
        }

        int minutes = Integer.parseInt(minutesText);
        int calories = Integer.parseInt(caloriesText);

        int steps = minutes * 100;

        databaseHelper.addActivity(
                exercise,
                minutes,
                calories,
                steps
        );

        loadSavedData();

        Toast.makeText(
                this,
                getString(R.string.activity_added, exercise),
                Toast.LENGTH_SHORT
        ).show();

        etExercise.setText("");
        etMinutes.setText("");
        etCalories.setText("");
    }
}
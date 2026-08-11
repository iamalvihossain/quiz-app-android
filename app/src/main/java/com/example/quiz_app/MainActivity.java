package com.example.quiz_app;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView questionTextView, correctAnswerDisplay;
    TextView totalQuestionTextView;
    Button ansA, ansB, ansC, ansD;
    Button btn_submit;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    boolean answerSubmitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalQuestionTextView = findViewById(R.id.total_question);
        questionTextView = findViewById(R.id.question);
        correctAnswerDisplay = findViewById(R.id.correct_answer_display);
        ansA = findViewById(R.id.ans_a);
        ansB = findViewById(R.id.ans_b);
        ansC = findViewById(R.id.ans_c);
        ansD = findViewById(R.id.ans_d);
        btn_submit = findViewById(R.id.btn_submit);

        totalQuestionTextView.setText("Total questions: " + totalQuestion);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        loadNewQuestion();
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }

        correctAnswerDisplay.setVisibility(View.GONE); // Hide the correct answer message
        resetOptions(); // Reset button colors and selection
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);
        selectedAnswer = "";
        answerSubmitted = false;
    }

    private void resetOptions() {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);
        ansA.setEnabled(true);
        ansB.setEnabled(true);
        ansC.setEnabled(true);
        ansD.setEnabled(true);
    }

    private void finishQuiz() {
        String passStatus;
        if (score >= totalQuestion * 0.6) {
            passStatus = "Passed";
        } else {
            passStatus = "Failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Your score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialog, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit) {
            if (answerSubmitted) {
                currentQuestionIndex++;
                loadNewQuestion(); // Load the next question after clicking submit
            }
        } else {
            if (answerSubmitted) return; // Prevent multiple clicks

            Button clickedButton = (Button) view;
            selectedAnswer = clickedButton.getText().toString();

            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
                clickedButton.setBackgroundColor(Color.GREEN);
                score++;
            } else {
                clickedButton.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }

            answerSubmitted = true;
            disableOptions();
        }
    }

    private void showCorrectAnswer() {
        correctAnswerDisplay.setVisibility(View.VISIBLE); // Show the correct answer message
        correctAnswerDisplay.setText(QuestionAnswer.correctAnswers[currentQuestionIndex] + " is the correct answer.");

        if (ansA.getText().toString().equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
            ansA.setBackgroundColor(Color.GREEN);
        } else if (ansB.getText().toString().equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
            ansB.setBackgroundColor(Color.GREEN);
        } else if (ansC.getText().toString().equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
            ansC.setBackgroundColor(Color.GREEN);
        } else if (ansD.getText().toString().equals(QuestionAnswer.correctAnswers[currentQuestionIndex])) {
            ansD.setBackgroundColor(Color.GREEN);
        }
    }

    private void disableOptions() {
        ansA.setEnabled(false);
        ansB.setEnabled(false);
        ansC.setEnabled(false);
        ansD.setEnabled(false);
    }
}

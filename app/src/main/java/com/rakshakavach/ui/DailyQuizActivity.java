package com.rakshakavach.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.rakshakavach.R;
import com.rakshakavach.data.TaskRepository;
import com.rakshakavach.model.QuizQuestion;
import com.rakshakavach.utils.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyQuizActivity extends AppCompatActivity {

    private List<QuizQuestion> questions;
    private int currentIndex = 0;
    private int score = 0;
    private boolean answered = false;

    private TextView tvCounter, tvQuestion, tvFeedback;
    private ProgressBar progressQuiz;
    private MaterialButton btnA, btnB, btnC, btnD, btnNext;
    private LinearLayout llActive, llComplete;
    private TextView tvScoreResult, tvQuizMessage;
    private PreferenceManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_quiz);

        prefs = new PreferenceManager(this);

        tvCounter = findViewById(R.id.tv_question_counter);
        tvQuestion = findViewById(R.id.tv_question);
        tvFeedback = findViewById(R.id.tv_feedback);
        progressQuiz = findViewById(R.id.progress_quiz);
        btnA = findViewById(R.id.btn_option_a);
        btnB = findViewById(R.id.btn_option_b);
        btnC = findViewById(R.id.btn_option_c);
        btnD = findViewById(R.id.btn_option_d);
        btnNext = findViewById(R.id.btn_next_question);
        llActive = findViewById(R.id.ll_quiz_active);
        llComplete = findViewById(R.id.ll_quiz_complete);
        tvScoreResult = findViewById(R.id.tv_quiz_score_result);
        tvQuizMessage = findViewById(R.id.tv_quiz_message);

        questions = TaskRepository.getDailyQuestions();
        Collections.shuffle(questions);
        if (questions.size() > 5) questions = questions.subList(0, 5);

        // If already done today, show result screen directly
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (today.equals(prefs.getLastQuizDate())) {
            score = questions.size(); // Assume perfect if they completed it
            showResult();
        } else {
            loadQuestion();
        }

        btnA.setOnClickListener(v -> checkAnswer(0));
        btnB.setOnClickListener(v -> checkAnswer(1));
        btnC.setOnClickListener(v -> checkAnswer(2));
        btnD.setOnClickListener(v -> checkAnswer(3));

        btnNext.setOnClickListener(v -> {
            currentIndex++;
            if (currentIndex >= questions.size()) {
                showResult();
            } else {
                loadQuestion();
            }
        });

        findViewById(R.id.btn_retake).setOnClickListener(v -> {
            currentIndex = 0;
            score = 0;
            Collections.shuffle(questions);
            llComplete.setVisibility(View.GONE);
            llActive.setVisibility(View.VISIBLE);
            loadQuestion();
        });

        findViewById(R.id.btn_back).setOnClickListener(v -> finish());
    }

    private void loadQuestion() {
        answered = false;
        QuizQuestion q = questions.get(currentIndex);

        tvCounter.setText((currentIndex + 1) + "/" + questions.size());
        progressQuiz.setProgress((currentIndex + 1) * 100 / questions.size());
        tvQuestion.setText(q.getQuestion());

        String[] opts = q.getOptions();
        btnA.setText("A.  " + opts[0]);
        btnB.setText("B.  " + opts[1]);
        btnC.setText("C.  " + opts[2]);
        btnD.setText("D.  " + opts[3]);

        resetOptionColors();
        tvFeedback.setVisibility(View.GONE);
        btnNext.setEnabled(false);
        btnNext.setText(currentIndex < questions.size() - 1 ? "NEXT QUESTION →" : "SEE RESULTS");
    }

    private void checkAnswer(int selectedIndex) {
        if (answered) return;
        answered = true;

        QuizQuestion q = questions.get(currentIndex);
        MaterialButton[] buttons = {btnA, btnB, btnC, btnD};

        // Mark correct green
        buttons[q.getCorrectIndex()].setBackgroundColor(Color.parseColor("#4CAF50"));
        buttons[q.getCorrectIndex()].setTextColor(Color.WHITE);

        if (selectedIndex == q.getCorrectIndex()) {
            score++;
            tvFeedback.setText("✅ Correct! " + q.getExplanation());
            tvFeedback.setBackgroundColor(Color.parseColor("#1A4D1A"));
            tvFeedback.setTextColor(Color.parseColor("#4CAF50"));
        } else {
            // Mark selected red
            buttons[selectedIndex].setBackgroundColor(Color.parseColor("#F44336"));
            buttons[selectedIndex].setTextColor(Color.WHITE);
            tvFeedback.setText("❌ Incorrect. " + q.getExplanation());
            tvFeedback.setBackgroundColor(Color.parseColor("#4D1A1A"));
            tvFeedback.setTextColor(Color.parseColor("#FF6B6B"));
        }

        tvFeedback.setVisibility(View.VISIBLE);
        btnNext.setEnabled(true);
    }

    private void resetOptionColors() {
        int strokeColor = Color.parseColor("#3A3A3A");
        int textColor = Color.parseColor("#FFFFFF");
        MaterialButton[] buttons = {btnA, btnB, btnC, btnD};
        for (MaterialButton btn : buttons) {
            btn.setBackgroundColor(Color.parseColor("#2C2C2C"));
            btn.setTextColor(textColor);
        }
    }

    private void showResult() {
        llActive.setVisibility(View.GONE);
        llComplete.setVisibility(View.VISIBLE);

        tvScoreResult.setText("You scored " + score + " / " + questions.size());

        String message;
        int points = 0;
        boolean isPerfect = (score == questions.size());

        if (isPerfect) {
            message = "Perfect score! You're a Safety Champion! 🏆";
            points = 30;
        } else if (score >= questions.size() * 0.8) {
            message = "Excellent! Great safety awareness! 🥇";
            points = 20;
        } else if (score >= questions.size() * 0.6) {
            message = "Good job! Keep learning to stay safe. 🥈";
            points = 10;
        } else {
            message = "Keep practicing! Safety knowledge saves lives. 📚\n(Try again for a perfect score!)";
            points = 0;
        }
        tvQuizMessage.setText(message);

        // Award points and lock for the day only if perfect or already done
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        boolean alreadyDoneToday = today.equals(prefs.getLastQuizDate());

        if (!alreadyDoneToday) {
            if (isPerfect) {
                prefs.setLastQuizDate(today);
                prefs.addToSafetyScore(points);
                prefs.incrementQuizCount();
                Toast.makeText(this, "Success! +" + points + " safety points earned!", Toast.LENGTH_SHORT).show();
                findViewById(R.id.btn_retake).setVisibility(View.GONE);
                tvQuizMessage.setText(message + "\n\nGreat job! You've completed today's quiz.");
            } else {
                Toast.makeText(this, "Try again to get a perfect score and unlock today's points!", Toast.LENGTH_LONG).show();
                findViewById(R.id.btn_retake).setVisibility(View.VISIBLE);
            }
        } else {
            tvQuizMessage.setText(message + "\n\n(Daily points already earned. Come back tomorrow!)");
            findViewById(R.id.btn_retake).setVisibility(View.GONE);
        }
    }
}

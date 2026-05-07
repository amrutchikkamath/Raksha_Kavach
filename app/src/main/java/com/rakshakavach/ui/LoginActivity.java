package com.rakshakavach.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rakshakavach.R;
import com.rakshakavach.data.AppDatabase;
import com.rakshakavach.data.UserDao;
import com.rakshakavach.data.UserEntity;
import com.rakshakavach.utils.AuthUtils;
import com.rakshakavach.utils.NotificationHelper;
import com.rakshakavach.utils.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout llSignIn, llSignUp;
    private TextView tabSignIn, tabSignUp;

    // Sign-in fields
    private TextInputEditText etSigninEmail, etSigninPassword;
    private TextView tvSigninError;

    // Sign-up fields
    private TextInputEditText etSignupName, etSignupEmail, etSignupPassword,
            etSignupConfirm, etSignupCompany;
    private RadioGroup rgRole;
    private TextView tvSignupError, tvPasswordStrength;
    private ProgressBar progressPasswordStrength;

    private UserDao userDao;
    private PreferenceManager prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs   = new PreferenceManager(this);
        userDao = AppDatabase.getInstance(this).userDao();

        // Skip login if already logged in
        if (prefs.isLoggedIn()) {
            goToMain();
            return;
        }

        bindViews();
        setupTabSwitching();
        setupSignIn();
        setupSignUp();
    }

    private void bindViews() {
        llSignIn   = findViewById(R.id.ll_signin);
        llSignUp   = findViewById(R.id.ll_signup);
        tabSignIn  = findViewById(R.id.tab_signin);
        tabSignUp  = findViewById(R.id.tab_signup);

        etSigninEmail    = findViewById(R.id.et_signin_email);
        etSigninPassword = findViewById(R.id.et_signin_password);
        tvSigninError    = findViewById(R.id.tv_signin_error);

        etSignupName     = findViewById(R.id.et_signup_name);
        etSignupEmail    = findViewById(R.id.et_signup_email);
        etSignupPassword = findViewById(R.id.et_signup_password);
        etSignupConfirm  = findViewById(R.id.et_signup_confirm);
        etSignupCompany  = findViewById(R.id.et_signup_company);
        rgRole           = findViewById(R.id.rg_role);
        tvSignupError    = findViewById(R.id.tv_signup_error);
        tvPasswordStrength      = findViewById(R.id.tv_password_strength);
        progressPasswordStrength = findViewById(R.id.progress_password_strength);
    }

    private void setupTabSwitching() {
        tabSignIn.setOnClickListener(v -> showSignIn());
        tabSignUp.setOnClickListener(v -> showSignUp());

        ((TextView) findViewById(R.id.tv_goto_signup)).setOnClickListener(v -> showSignUp());
        ((TextView) findViewById(R.id.tv_goto_signin)).setOnClickListener(v -> showSignIn());
    }

    private void showSignIn() {
        llSignIn.setVisibility(View.VISIBLE);
        llSignUp.setVisibility(View.GONE);
        tabSignIn.setBackgroundColor(Color.parseColor("#FFD600"));
        tabSignIn.setTextColor(Color.parseColor("#1A1A1A"));
        tabSignUp.setBackgroundColor(Color.parseColor("#2C2C2C"));
        tabSignUp.setTextColor(Color.parseColor("#BDBDBD"));
    }

    private void showSignUp() {
        llSignIn.setVisibility(View.GONE);
        llSignUp.setVisibility(View.VISIBLE);
        tabSignUp.setBackgroundColor(Color.parseColor("#FFD600"));
        tabSignUp.setTextColor(Color.parseColor("#1A1A1A"));
        tabSignIn.setBackgroundColor(Color.parseColor("#2C2C2C"));
        tabSignIn.setTextColor(Color.parseColor("#BDBDBD"));
    }

    // ── SIGN IN ──────────────────────────────────────────────────────────────

    private void setupSignIn() {
        MaterialButton btnSignIn = findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(v -> attemptSignIn());
    }

    private void attemptSignIn() {
        String email    = text(etSigninEmail);
        String password = text(etSigninPassword);

        if (email.isEmpty() || password.isEmpty()) {
            showSignInError("Please fill in all fields.");
            return;
        }
        if (!AuthUtils.isValidEmail(email)) {
            showSignInError("Enter a valid email address.");
            return;
        }

        tvSigninError.setVisibility(View.GONE);
        String hash = AuthUtils.sha256(password);

        Executors.newSingleThreadExecutor().execute(() -> {
            UserEntity user = userDao.login(email.toLowerCase().trim(), hash);
            runOnUiThread(() -> {
                if (user != null) {
                    prefs.setLoggedInUser(user.id, user.fullName);
                    Toast.makeText(this, "Welcome back, " + user.fullName + "! 🦺", Toast.LENGTH_SHORT).show();
                    goToMain();
                } else {
                    // Distinguish wrong password vs unknown email
                    Executors.newSingleThreadExecutor().execute(() -> {
                        int exists = userDao.emailExists(email.toLowerCase().trim());
                        runOnUiThread(() -> {
                            if (exists > 0) {
                                showSignInError("Incorrect password. Please try again.");
                            } else {
                                showSignInError("No account found for this email. Please sign up.");
                            }
                        });
                    });
                }
            });
        });
    }

    private void showSignInError(String msg) {
        tvSigninError.setText("⚠ " + msg);
        tvSigninError.setVisibility(View.VISIBLE);
    }

    // ── SIGN UP ──────────────────────────────────────────────────────────────

    private void setupSignUp() {
        // Live password strength
        etSignupPassword.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updatePasswordStrength(s.toString());
            }
        });

        MaterialButton btnSignUp = findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(v -> attemptSignUp());
    }

    private void updatePasswordStrength(String password) {
        if (password.isEmpty()) {
            progressPasswordStrength.setProgress(0);
            tvPasswordStrength.setText("");
            return;
        }
        String strength = AuthUtils.getPasswordStrength(password);
        int progress;
        int color;
        switch (strength) {
            case "Strong": progress = 3; color = Color.parseColor("#4CAF50"); break;
            case "Medium": progress = 2; color = Color.parseColor("#FF9800"); break;
            default:       progress = 1; color = Color.parseColor("#F44336");
        }
        progressPasswordStrength.setProgress(progress);
        progressPasswordStrength.getProgressDrawable()
                .setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN);
        tvPasswordStrength.setText(strength);
        tvPasswordStrength.setTextColor(color);
    }

    private void attemptSignUp() {
        String name     = text(etSignupName);
        String email    = text(etSignupEmail).toLowerCase().trim();
        String password = text(etSignupPassword);
        String confirm  = text(etSignupConfirm);
        String company  = text(etSignupCompany);

        String role = (rgRole.getCheckedRadioButtonId() == R.id.rb_supervisor)
                ? "supervisor" : "worker";

        // Validation
        if (name.isEmpty()) { showSignUpError("Full name is required."); return; }
        if (!AuthUtils.isValidEmail(email)) { showSignUpError("Enter a valid email address."); return; }
        if (!AuthUtils.isValidPassword(password)) {
            showSignUpError("Password must be at least 6 characters.");
            return;
        }
        if (!password.equals(confirm)) { showSignUpError("Passwords do not match."); return; }

        tvSignupError.setVisibility(View.GONE);
        String hash = AuthUtils.sha256(password);

        Executors.newSingleThreadExecutor().execute(() -> {
            // Check email uniqueness
            int exists = userDao.emailExists(email);
            if (exists > 0) {
                runOnUiThread(() -> showSignUpError("This email is already registered. Please sign in."));
                return;
            }

            UserEntity newUser = new UserEntity(
                    name, email, hash, role, company, System.currentTimeMillis()
            );
            try {
                long newId = userDao.insertUser(newUser);
                runOnUiThread(() -> {
                    prefs.setLoggedInUser((int) newId, name);
                    Toast.makeText(this,
                            "Account created! Welcome, " + name + "! 🎉",
                            Toast.LENGTH_LONG).show();
                    goToMain();
                });
            } catch (Exception e) {
                runOnUiThread(() -> showSignUpError("Registration failed. Please try again."));
            }
        });
    }

    private void showSignUpError(String msg) {
        tvSignupError.setText("⚠ " + msg);
        tvSignupError.setVisibility(View.VISIBLE);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private String text(TextInputEditText et) {
        return et.getText() != null ? et.getText().toString().trim() : "";
    }

    private void goToMain() {
        checkAndStartPeriodicReminders();
        startActivity(new Intent(this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    private void checkAndStartPeriodicReminders() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (!today.equals(prefs.getLastCheckDate())) {
            NotificationHelper.schedulePeriodicReminder(this);
        }
    }
}

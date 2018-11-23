package com.sourcey.materiallogindemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.SharedPreferences;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText _username;
    EditText _passwordText;
    Button _loginButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_PREFS_NAME = "name";
    public static final String SHARED_PREFS_LOGGED_IN = "logged_in";

    public boolean logged_in = false;

    //private String load_username;
    private boolean load_logged_in;

    //TextView _signupLink = findViewById(R.id.link_signup);
    //TextView _resetLink = findViewById(R.id.link_reset);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("myTag", "inside oncreate() in login activity");

        loadData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _loginButton = findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _username = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
    }

    public void login() {
        Log.d("myTag", "inside login() in login activity");

        if (!validate()) {
            Log.d("myTag", "inside !validate() in login activity");
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...Please Wait");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.d("myTag", "inside run() in login activity");

                        // On complete call either onLoginSuccess or onLoginFailed
                        Log.d("myTag", "inside run() before onloginsuccess() in login activity");
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    @Override
    public void onBackPressed() {
        Log.d("myTag", "inside onbackpressed in login activity");

        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Log.d("myTag", "inside login success in login activity");

       // String username = _username.getText().toString();
        logged_in = true;

        saveData();

        _loginButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
       // intent.putExtra("Username", username);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onLoginFailed() {
        Log.d("myTag", "inside login failed in login activity");

        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        Log.d("myTag", "inside validate in login activity");
        boolean valid = true;

        String username = _username.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _username.setError("username is empty");
            valid = false;
        } else {
            _username.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void saveData(){
        Log.d("myTag", "inside saveData in login activity");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SHARED_PREFS_NAME, _username.getText().toString());
        editor.putBoolean(SHARED_PREFS_LOGGED_IN, logged_in);

        editor.apply();
    }

    public void loadData() {
        Log.d("myTag", "inside loadData in login activity");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        load_logged_in = sharedPreferences.getBoolean(SHARED_PREFS_LOGGED_IN, false);

        if (load_logged_in) {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

}

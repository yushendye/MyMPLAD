package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.VideoView;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.edt_login_username);
        password = findViewById(R.id.edt_login_password);
    }

    public boolean validate(){
        boolean valid = true;
        if(username.getText().toString().equals("") || username.getText().toString().isEmpty()) {
            username.setError("Enter your username please");
            valid = false;
        }
        if(password.getText().toString().equals("") || username.getText().toString().isEmpty()) {
            password.setError("Please enter your password");
            valid = false;
        }
        return  valid;
    }

    public void login(View view){
        if(validate()){
            Intent login_intent = new Intent(LoginActivity.this, UserPortal.class);
            startActivity(login_intent);
        }
    }

    public void register(View view){
        Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register_intent);
    }
}
package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;

    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.edt_login_username);
        password = findViewById(R.id.edt_login_password);

        helper = new DbHelper(getApplicationContext());
        //helper.user_register(1, "chinmay", "shendye", "Upendra", "chinmaester","Maharashtra", "Ratnagiri", "Nachane", "Male", "9975086979", "443434343", "123456");
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
            List<User> logged_in_details = helper.login(username.getText().toString(), password.getText().toString());

            if(logged_in_details.size() > 0) {
                login_intent.putExtra("username", username.getText().toString());
                startActivity(login_intent);
            }
            else
                Toast.makeText(this, "Invalid login details!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View view){
        Intent register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(register_intent);
    }

    public void mp_login(View view){
        Intent mp_intent = new Intent(LoginActivity.this, MPLogin.class);
        startActivity(mp_intent);
    }
}
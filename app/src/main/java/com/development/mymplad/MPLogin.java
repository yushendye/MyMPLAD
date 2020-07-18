package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MPLogin extends AppCompatActivity {
    EditText edt_mp_username;
    EditText edt_mp_password;
    DbHelper helper;
    String PREF = LoginActivity.PREF;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_login);

        edt_mp_username = findViewById(R.id.edt_mp_login_username);
        edt_mp_password = findViewById(R.id.edt_mp_login_password);

        helper = new DbHelper(getApplicationContext());
        preferences = getSharedPreferences(PREF, MODE_PRIVATE);

        if(preferences.contains("mp_name")){
            startActivity(new Intent(MPLogin.this, ComplaintToMp.class));
        }
    }

    public void mp_login(View view){
        Intent intent = new Intent(MPLogin.this, ComplaintToMp.class);
        String mp_name = helper.mp_login(edt_mp_username.getText().toString(), edt_mp_password.getText().toString());

        if(!mp_name.isEmpty()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("mp_name",mp_name);
            editor.apply();
            editor.commit();
            startActivity(intent);
            finish();
        }
        else
            Toast.makeText(getApplicationContext(), "Invalid credentials!!", Toast.LENGTH_LONG).show();
    }
}
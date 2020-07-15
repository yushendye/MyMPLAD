package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MPLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_p_login);
    }

    public void mp_login(View view){
        Intent intent = new Intent(MPLogin.this, ComplaintToMp.class);
        startActivity(intent);
    }
}
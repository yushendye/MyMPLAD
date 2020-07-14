package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserPortal extends AppCompatActivity {
    Toolbar toolbar;
    List<String> states;
    List<String> districts;
    String state_district_json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        load_state_district_json();
        load_states();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    public void  load_state_district_json(){
        state_district_json = "";
        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.states_districts);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int word;

            while ((word = reader.read()) != -1){
                state_district_json += String.valueOf((char) word);
            }
        }catch (Exception e){
            toast(e.getMessage());
        }
    }

    public void load_states(){
        states = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(state_district_json);
            JSONArray states_array = data.getJSONArray("states");
            for(int i = 0; i < states_array.length(); i++){
                JSONObject state = states_array.getJSONObject(i);
                states.add(state.getString("state"));
            }
        }catch (JSONException jsonex){
            toast("JSON exception!!");
        }
    }

    public void load_districts(String state){

    }

    public void toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void short_toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
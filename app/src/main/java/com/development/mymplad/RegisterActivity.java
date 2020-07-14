package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    Spinner spn_state;
    Spinner spn_city;

    List<String> state_list;
    List<String> city_list;
    String state_district_json;

    ArrayAdapter<String> state_adapter, district_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spn_state = findViewById(R.id.spn_state);
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_selected = state_list.get(position);
                load_districts(state_selected);
                init_district_spinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_city = findViewById(R.id.spn_city);

        load_state_district_json();
        load_states();
        init_state_spinner();
    }

    public void init_state_spinner(){
        state_adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, state_list);
        spn_state.setAdapter(state_adapter);
    }

    public void init_district_spinner(){
        district_adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_item, city_list);
        spn_city.setAdapter(district_adapter);
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
        state_list = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(state_district_json);
            JSONArray states_array = data.getJSONArray("states");
            for(int i = 0; i < states_array.length(); i++){
                JSONObject state = states_array.getJSONObject(i);
                state_list.add(state.getString("state"));
            }
        }catch (JSONException jsonex){
            toast("JSON exception!!");
        }
    }

    public void load_districts(String state){
        city_list = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(state_district_json);
            JSONArray states_array = data.getJSONArray("states");
            for(int i = 0; i < states_array.length(); i++){
                JSONObject curr_state = states_array.getJSONObject(i);
                if(curr_state.getString("state").equals(state)){
                    JSONArray dists = curr_state.getJSONArray("districts");
                    for(int j = 0; j < dists.length(); j++)
                        city_list.add(dists.getString(j));
                }
            }
        }catch (JSONException jsonex){
            toast("JSON exception!! at districts");
        }
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
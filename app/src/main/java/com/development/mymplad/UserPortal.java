package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
    List<String> problem_list;


    String state_district_json;

    Spinner spn_state, spn_dis, spn_problem;

    ArrayAdapter<String> state_adapter, district_adapter, problem_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);

        spn_state = findViewById(R.id.spn_state);
        spn_dis = findViewById(R.id.spn_city);
        spn_problem = findViewById(R.id.spn_problem);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        load_state_district_json();
        load_states();

        init_state_spinner();
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_selected = states.get(position);
                load_districts(state_selected);
                init_district_spinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        load_problem_list();
        init_problem_list();
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
        districts = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(state_district_json);
            JSONArray states_array = data.getJSONArray("states");
            for(int i = 0; i < states_array.length(); i++){
                JSONObject curr_state = states_array.getJSONObject(i);
                if(curr_state.getString("state").equals(state)){
                    JSONArray dists = curr_state.getJSONArray("districts");
                    for(int j = 0; j < dists.length(); j++)
                        districts.add(dists.getString(j));
                }
            }
        }catch (JSONException jsonex){
            toast("JSON exception!! at districts");
        }
    }

    public void init_state_spinner(){
        state_adapter = new ArrayAdapter<>(UserPortal.this, android.R.layout.simple_expandable_list_item_1, states);
        spn_state.setAdapter(state_adapter);
    }

    public void init_district_spinner(){
        district_adapter = new ArrayAdapter<>(UserPortal.this, android.R.layout.simple_expandable_list_item_1, districts);
        spn_dis.setAdapter(district_adapter);
    }


    public void toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void short_toast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void load_problem_list(){
        problem_list = new ArrayList<>();
        problem_list.add(new String("Electricity Issue"));
        problem_list.add(new String("Water Issue"));
        problem_list.add(new String("Garbage Issue"));
        problem_list.add(new String("Cleanliness issue"));
        problem_list.add(new String("Transport Issue"));
    }

    public void init_problem_list(){
        problem_adapter = new ArrayAdapter(UserPortal.this, android.R.layout.simple_expandable_list_item_1, problem_list);
        spn_problem.setAdapter(problem_adapter);
    }
}
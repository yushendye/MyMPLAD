package com.development.mymplad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class UserPortal extends AppCompatActivity {
    Toolbar toolbar;
    List<String> states;
    List<String> districts;
    List<String> problem_list;
    List<String> mp_list;

    SharedPreferences preferences;
    DbHelper helper;

    String state_district_json, mps_data_json;
    Spinner spn_state, spn_dis, spn_problem, spn_mp;
    EditText edt_problem_desc;
    ArrayAdapter<String> state_adapter, district_adapter, problem_adapter, mp_adapter;
    User login_user;

    public int getStateIndex(String state){
        int pos = 0;
        for(int i = 0; i < states.size(); i++)
            if(states.get(i).equals(state))
                pos = i;
        return pos;
    }

    public int getDistIndex(String dis){
        int pos = 0;
        for(int i = 0; i < states.size(); i++)
            if(districts.get(i).equals(dis))
                pos = i;
        return pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portal);

        spn_state = findViewById(R.id.spn_state);
        spn_dis = findViewById(R.id.spn_city);
        spn_problem = findViewById(R.id.spn_problem);
        spn_mp = findViewById(R.id.spn_mp);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edt_problem_desc = findViewById(R.id.edt_problem_desc);

        preferences = getSharedPreferences(LoginActivity.PREF, MODE_PRIVATE);
        helper = new DbHelper(getApplicationContext());
        login_user = new User();
        List<User> list = helper.login(preferences.getString("username", ""), preferences.getString("password", ""));
        login_user = list.get(0);
        load_state_district_json();
        load_states();
        init_state_spinner();
        load_problem_list();
        init_problem_list();
        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_selected = states.get(position);

                load_districts(state_selected);
                init_district_spinner();

                load_mps_data(state_selected);
                init_mp_spinner();

                spn_dis.setSelection(getDistIndex(login_user.getDistrict()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_state.setSelection(getStateIndex(login_user.getState()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.mn_quit:
                super.finish();
                break;
            case R.id.mn_settings:
                Intent i = new Intent(UserPortal.this, UserSettings.class);
                startActivity(i);
                break;
            case R.id.mn_logout:
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                editor.commit();
                startActivity(new Intent(UserPortal.this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init_state_spinner(){
        state_adapter = new ArrayAdapter<>(UserPortal.this, android.R.layout.simple_expandable_list_item_1, states);
        spn_state.setAdapter(state_adapter);
    }

    public void init_district_spinner(){
        district_adapter = new ArrayAdapter<>(UserPortal.this, android.R.layout.simple_expandable_list_item_1, districts);
        spn_dis.setAdapter(district_adapter);
    }

    public void init_mp_spinner(){
        mp_adapter = new ArrayAdapter<>(UserPortal.this, android.R.layout.simple_expandable_list_item_1, mp_list);
        spn_mp.setAdapter(mp_adapter);
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

    public void load_mps_data(final String state){
        mp_list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.mps)));
        try {
            mps_data_json = reader.readLine();
        }catch (Exception e){}

        try {
            JSONObject object = new JSONObject(mps_data_json);
            JSONArray data = object.getJSONArray("data");

            for(int i = 0; i < data.length(); i++){
                JSONArray array = data.getJSONArray(i);
                if(array.getString(8).equals(state))
                    mp_list.add(array.getString(0));
            }
        }catch (JSONException ex){
            toast(ex.getMessage());
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
        problem_list.add(new String("Transport Issue"));
    }

    public void init_problem_list(){
        problem_adapter = new ArrayAdapter(UserPortal.this, android.R.layout.simple_expandable_list_item_1, problem_list);
        spn_problem.setAdapter(problem_adapter);
    }

    public void reg_complaint(View view){
//        public long insert_complaint(int mp_id, String sector, String dist, String state, String title, String description,
//        String cust_name, String ct_mobile, String ct_address, int status){
        long result = helper.insert_complaint(
                spn_mp.getSelectedItem().toString(), spn_problem.getSelectedItem().toString(), spn_dis.getSelectedItem().toString(),
                spn_state.getSelectedItem().toString(), "Problem related to " + spn_problem.getSelectedItem().toString(),
                edt_problem_desc.getText().toString(), login_user.getFname() + " " + login_user.getLname(),
                login_user.getContact(), login_user.getAddress());
        if(result != -1)
            toast("Your complaint has been registered!!");
        else
            toast("Some unexpected error has occurred!!");
    }
}
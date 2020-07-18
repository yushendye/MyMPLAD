package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class UserSettings extends AppCompatActivity {
    EditText edt_fname, edt_lname, edt_mname, edt_username, edt_address, edt_phone, edt_adhar, edt_pass1, edt_pass2;
    RadioGroup rg_gender;

    Spinner spn_state;
    Spinner spn_city;

    List<String> state_list;
    List<String> city_list;
    String state_district_json;

    ArrayAdapter<String> state_adapter, district_adapter;
    SharedPreferences preferences;
    String PREF = LoginActivity.PREF;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        rg_gender = findViewById(R.id.rg_gender);
        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_mname = findViewById(R.id.edt_mname);
        edt_username = findViewById(R.id.edt_username);
        edt_address = findViewById(R.id.edt_address);
        edt_phone = findViewById(R.id.edt_phone);
        edt_adhar = findViewById(R.id.adhar);
        edt_pass1 = findViewById(R.id.edt_pass1);
        edt_pass2 = findViewById(R.id.pass2);

        spn_state = findViewById(R.id.spn_state);

        preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        helper = new DbHelper(getApplicationContext());

        final User user = helper.login(preferences.getString("username", ""), preferences.getString("password", "")).get(0);

        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_selected = state_list.get(position);
                load_districts(state_selected);
                init_district_spinner();
                spn_city.setSelection(getDistIndex(user.getDistrict()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_city = findViewById(R.id.spn_city);

        load_state_district_json();
        load_states();
        init_state_spinner();

        edt_fname.setText(user.getFname());
        edt_lname.setText(user.getLname());
        edt_mname.setText(user.getMname());
        edt_username.setText(user.getUsername());
        edt_address.setText(user.getAddress());
        edt_phone.setText(user.getContact());
        edt_adhar.setText(user.getAadhar());

        edt_pass1.setText(user.getPassword());
        edt_pass2.setText(user.getPassword());

        if(user.getGender().equals("Male")){
            RadioButton btn = findViewById(R.id.male);
            btn.setChecked(true);
        }else{
            RadioButton btn = findViewById(R.id.female);
            btn.setChecked(true);
        }
        spn_state.setSelection(getStateIndex(user.getState()));
    }

    public int getStateIndex(String state){
        int pos = 0;
        for(int i = 0; i < state_list.size(); i++)
            if(state_list.get(i).equals(state))
                pos = i;
        return pos;
    }

    public int getDistIndex(String dis){
        int pos = 0;
        for(int i = 0; i < state_list.size(); i++)
            if(city_list.get(i).equals(dis))
                pos = i;
        return pos;
    }

    public void init_state_spinner(){
        state_adapter = new ArrayAdapter<>(UserSettings.this, android.R.layout.simple_spinner_item, state_list);
        spn_state.setAdapter(state_adapter);
    }

    public void init_district_spinner(){
        district_adapter = new ArrayAdapter<>(UserSettings.this, android.R.layout.simple_spinner_item, city_list);
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

    public void save_changes(View view){
        String u = preferences.getString("username", "");
        String p = preferences.getString("password", "");
        RadioButton btn = findViewById(rg_gender.getCheckedRadioButtonId());
        String gender = btn.getText().toString();
        long result = helper.update_settings(u, p, edt_fname.getText().toString(), edt_lname.getText().toString(), edt_mname.getText().toString(), edt_username.getText().toString(), spn_state.getSelectedItem().toString(), spn_city.getSelectedItem().toString(), edt_address.getText().toString(), gender, edt_phone.getText().toString(), edt_adhar.getText().toString(), edt_pass1.getText().toString());

        if(result != -1) {
            toast("Settings updated successfully!!, Please login again");
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(UserSettings.this, LoginActivity.class));
            finish();
        }
        else
            toast("Some error occurred!!");
    }
}
package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
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

public class RegisterActivity extends AppCompatActivity {
    EditText edt_fname, edt_lname, edt_mname, edt_username, edt_address, edt_phone, edt_adhar, edt_pass1, edt_pass2;
    RadioGroup rg_gender;
    Spinner spn_state;
    Spinner spn_city;

    List<String> state_list;
    List<String> city_list;
    ArrayAdapter<String> state_adapter, district_adapter;

    DbHelper helper;

    String state_district_json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_mname = findViewById(R.id.edt_mname);

        edt_username = findViewById(R.id.edt_username);
        edt_address = findViewById(R.id.edt_address);
        edt_phone = findViewById(R.id.edt_phone);
        edt_adhar = findViewById(R.id.adhar);

        edt_pass1 = findViewById(R.id.edt_pass1);
        edt_pass2 = findViewById(R.id.pass2);
        rg_gender = findViewById(R.id.rg_gender);
        spn_state = findViewById(R.id.spn_state);

        helper = new DbHelper(getApplicationContext());
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
        state_adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_expandable_list_item_1, state_list);
        spn_state.setAdapter(state_adapter);
    }

    public void init_district_spinner(){
        district_adapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_expandable_list_item_1, city_list);
        spn_city.setAdapter(district_adapter);
    }

    public void  load_state_district_json(){
        state_district_json = "";
        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.states_districts);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            int word;
            state_district_json = "";
            while ((word = reader.read()) != -1){
                state_district_json = state_district_json.concat(String.valueOf((char) word));
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
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public boolean validate(){
        boolean valid = true;

        if(edt_fname.getText().toString().isEmpty()) {
            valid = false;
            edt_fname.setError("Please enter your first name");
        }

        if(edt_username.getText().toString().isEmpty() || edt_username.getText().toString().contains(" ")){
            valid = false;
            edt_username.setError("Please fill this field without any space");
        }
        if(edt_lname.getText().toString().isEmpty()) {
            valid = false;
            edt_lname.setError("Please enter your last name");
        }

        if(!edt_pass1.getText().toString().equals(edt_pass2.getText().toString())){
            valid = false;
            edt_pass2.setError("Password mismatch");
        }

        if(edt_phone.getText().toString().isEmpty()){
            valid = false;
            edt_phone.setError("Please enter your phone number");
        }

        if(edt_pass1.getText().toString().isEmpty() || edt_pass2.getText().toString().isEmpty()){
            valid = false;
            edt_pass1.setError("Please enter your password");
            edt_pass2.setError("Please re-enter the password");
        }

        if(! edt_pass1.getText().toString().equals(edt_pass2.getText().toString())){
            valid = false;
            edt_pass2.setError("Please re-enter the password");
        }
        return valid;
    }

    public void register(View view){
        long result;
        if(validate()){
            toast("Address : " + edt_address.getText().toString());
            RadioButton gen = findViewById(rg_gender.getCheckedRadioButtonId());
            result = helper.user_register(
                                edt_fname.getText().toString(),
                                edt_lname.getText().toString(),
                                edt_mname.getText().toString(),
                                edt_username.getText().toString(),
                                spn_state.getSelectedItem().toString(),
                                spn_city.getSelectedItem().toString(),
                                edt_address.getText().toString(),
                                gen.getText().toString(),
                                edt_phone.getText().toString(),
                                edt_adhar.getText().toString(),
                                edt_pass1.getText().toString()
            );
        }
        else
            result = -1;

        if(result != -1) {
            clear();
            toast("Registered successfully");
            edt_fname.setFocusable(true);
        }
    }

    public void clear(){
        edt_fname.setText("");
        edt_lname.setText("");
        edt_mname.setText("");
        edt_phone.setText("");
        edt_address.setText("");
        edt_pass2.setText("");
        edt_pass1.setText("");
        edt_username.setText("");
    }
}
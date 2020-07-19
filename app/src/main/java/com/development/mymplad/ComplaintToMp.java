package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ComplaintToMp extends AppCompatActivity {
    List<Complaint> complaintList;
    RecyclerView rcv_complaints;
    SearchView sv_searchview;
    ComplaintAdapter complaintAdapter;
    SharedPreferences preferences;
    String PREF = LoginActivity.PREF;
    String mp_name;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_to_mp);

        sv_searchview = findViewById(R.id.sv_searchview);

        sv_searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(! newText.isEmpty())
                    complaintAdapter.getFilter().filter(newText);
                return false;
            }
        });
        rcv_complaints = findViewById(R.id.rcv_complaints);

        helper = new DbHelper(getApplicationContext());
        preferences = getSharedPreferences(PREF, MODE_PRIVATE);
        mp_name = preferences.getString("mp_name", "");
        init_complaints_rcv();
    }

    public void load_complaints(){
        complaintList = new ArrayList<>();
        complaintList = helper.get_complaints(mp_name);

        if(complaintList.size() <= 0)
            Toast.makeText(this, "No Pending Complaints found!!", Toast.LENGTH_SHORT).show();
    }

    public void init_complaints_rcv(){
        load_complaints();
        complaintAdapter = new ComplaintAdapter(complaintList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcv_complaints.setLayoutManager(layoutManager);
        rcv_complaints.setItemAnimator(new DefaultItemAnimator());
        rcv_complaints.setAdapter(complaintAdapter);
    }
}
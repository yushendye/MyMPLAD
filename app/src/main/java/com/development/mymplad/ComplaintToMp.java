package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class ComplaintToMp extends AppCompatActivity {
    List<Complaint> complaintList;
    RecyclerView rcv_complaints;
    SearchView sv_searchview;
    ComplaintAdapter complaintAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_to_mp);

        sv_searchview = findViewById(R.id.sv_searchview);
        rcv_complaints = findViewById(R.id.rcv_complaints);

        init_complaints_rcv();
    }

    public void load_complaints(){
        complaintList = new ArrayList<>();
        complaintList.add(new Complaint(1, "https://timesofindia.indiatimes.com/thumb/msid-64992955,width-1200,height-900,resizemode-4/.jpg", "Complaint Related to Road", "Very bad condition of roads!!", "Ratnagiri, Maharashtra"));
        complaintList.add(new Complaint(2, "https://5.imimg.com/data5/LI/TO/MY-17243163/electric-poles-500x500.jpg", "Complaint Related to Electricity", "Very bad condition of poles!!", "Ratnagiri, Maharashtra"));
        complaintList.add(new Complaint(3, "https://writescape.ca/site/wp-content/uploads/2019/07/garbage-2729608__340-519x300.jpg", "Complaint Related to Garbage", "Very bad condition of dustbins!!", "Ratnagiri, Maharashtra"));
        complaintList.add(new Complaint(4, "https://5.imimg.com/data5/LI/TO/MY-17243163/electric-poles-500x500.jpg", "Complaint Related to Electricity", "Very bad condition of poles!!", "Ratnagiri, Maharashtra"));
        complaintList.add(new Complaint(5, "https://timesofindia.indiatimes.com/thumb/msid-64992955,width-1200,height-900,resizemode-4/.jpg", "Complaint Related to Road", "Very bad condition of roads!!", "Ratnagiri, Maharashtra"));
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
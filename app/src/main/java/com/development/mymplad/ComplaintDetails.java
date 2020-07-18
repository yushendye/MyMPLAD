package com.development.mymplad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ComplaintDetails extends AppCompatActivity {
    ImageView imv_logo;
    TextView det_title, det_desc, det_loc, det_username, det_contact;
    DbHelper helper;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_details);

        imv_logo = findViewById(R.id.imv_det_complaint_logo);
        det_title = findViewById(R.id.complaint_head);
        det_desc = findViewById(R.id.det_complain_desc);
        det_loc = findViewById(R.id.det_complaint_loc);
        det_username = findViewById(R.id.det_cust_name);
        det_contact = findViewById(R.id.det_cust_contact);

        helper = new DbHelper(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getIntExtra("complaint_id", 1);

        Complaint complaint = helper.get_complaint(id + 1);

        Picasso.get().load(complaint.getUrl()).into(imv_logo);
        det_title.setText(complaint.getTitle());
        det_desc.setText(complaint.getDescription());
        det_loc.setText(complaint.getLocation());
        det_username.setText(complaint.getCustomer_name());
        det_contact.setText(complaint.getContact());
    }

    public void resolve(View view){
        long res = helper.resolve(id + 1);
        if(res != -1){
            Toast.makeText(this, "Resolved Successfully!!", Toast.LENGTH_LONG).show();
        }
    }

    public void put_on_wait(View view){
        long res = helper.put_on_wait(id + 1);
        if(res != -1){
            Toast.makeText(this, "You have put this issue on wait, you will keep seeing this until you resolve it!!", Toast.LENGTH_LONG).show();
        }
    }
}
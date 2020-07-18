package com.development.mymplad;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    List<Complaint> complaintList;
    public ComplaintAdapter(){

    }

    public ComplaintAdapter(List<Complaint> complaints){
        this.complaintList = complaints;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complain_ui, parent, false);
        ComplaintViewHolder viewHolder = new ComplaintViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, final int position) {
        Complaint complaint = complaintList.get(position);

        Picasso.get().load(complaint.getUrl()).into(holder.complaint_logo);
        holder.title.setText(complaint.getTitle());
        holder.description.setText(complaint.getDescription());
        holder.location.setText(complaint.getLocation());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ComplaintDetails.class);
                intent.putExtra("complaint_id", position);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    class ComplaintViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout item;
        ImageView complaint_logo;
        TextView title, description, location;
        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.rcv_recycler_item);
            complaint_logo = itemView.findViewById(R.id.imv_complaint_logo);
            title = itemView.findViewById(R.id.complaint_head);
            description = itemView.findViewById(R.id.complain_desc);
            location = itemView.findViewById(R.id.complaint_loc);
        }
    }
}

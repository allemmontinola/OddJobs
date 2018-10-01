package com.example.allem.revised_capstone.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allem.revised_capstone.Model.User_AppointModel_List;
import com.example.allem.revised_capstone.R;
import com.example.allem.revised_capstone.User.FragmentAppointListUser;

import java.util.ArrayList;

public class User_AppointAdapter_List extends RecyclerView.Adapter<User_AppointAdapter_List.ViewHolder>{
    private ArrayList<User_AppointModel_List> valueList;
    private ArrayList<User_AppointModel_List> arrayList;
    private FragmentActivity context;

    public User_AppointAdapter_List(FragmentActivity ctx, ArrayList<User_AppointModel_List> valueList){
        this.valueList = valueList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(FragmentAppointListUser.user_appoint_List);
        this.context = ctx;

    }

    @Override
    public User_AppointAdapter_List.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_user_appoint_lists, parent, false);
        return new User_AppointAdapter_List.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(User_AppointAdapter_List.ViewHolder holder, int position) {
        holder.tvId.setText(valueList.get(position).getId());
        holder.tvFullName.setText(valueList.get(position).getAppointedFullName());
        holder.tvEmail.setText(valueList.get(position).getAppointedTo());
        holder.tvTime.setText(valueList.get(position).getTime());
        holder.tvDate.setText(valueList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvTime, tvDate,  tvContact, tvId;
        ImageView image;
        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.id_User_Appoint_List);
            tvFullName = itemView.findViewById(R.id.fullName_User_Appoint_List);
            tvEmail = itemView.findViewById(R.id.email_user_appoint_list);
            tvTime = itemView.findViewById(R.id.time_user_appoint_list);
            tvDate = itemView.findViewById(R.id.date_user_appoint_list);
            tvContact = itemView.findViewById(R.id.contact_user_appoint_list);

        }
    }
}

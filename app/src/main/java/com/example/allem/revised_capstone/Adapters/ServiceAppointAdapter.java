package com.example.allem.revised_capstone.Adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allem.revised_capstone.Model.ServiceAppointModel;
import com.example.allem.revised_capstone.R;
import com.example.allem.revised_capstone.ServiceProvider.FragmentAppointService;

import java.util.ArrayList;

public class ServiceAppointAdapter extends RecyclerView.Adapter<ServiceAppointAdapter.ViewHolder> {
    private ArrayList<ServiceAppointModel> valueList;
    private ArrayList<ServiceAppointModel> arrayList;
    private FragmentActivity context;

    public ServiceAppointAdapter(FragmentActivity ctx, ArrayList<ServiceAppointModel> valueList) {
        this.valueList = valueList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(FragmentAppointService.service_appoint_List);
        this.context = ctx;

    }

    @Override
    public ServiceAppointAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_appoint_service, parent, false);
        return new ServiceAppointAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServiceAppointAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(valueList.get(position).getId());
        holder.tvFullName.setText(valueList.get(position).getAppointedFullName());
        holder.tvEmail.setText(valueList.get(position).getAppointedFrom());
        holder.tvTime.setText(valueList.get(position).getTime());
        holder.tvDate.setText(valueList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvTime, tvDate, tvContact, tvId;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.id_service_appoint);
            tvFullName = itemView.findViewById(R.id.fullName_service_appoint);
            tvEmail = itemView.findViewById(R.id.email_service_appoint);
            tvTime = itemView.findViewById(R.id.time_service_appoint);
            tvDate = itemView.findViewById(R.id.date_service_appoint);
            tvContact = itemView.findViewById(R.id.contact_service_appoint);

            image = itemView.findViewById(R.id.iv_profile_service_appoint);
        }
    }
}

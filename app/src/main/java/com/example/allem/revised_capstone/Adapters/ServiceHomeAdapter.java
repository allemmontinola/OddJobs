package com.example.allem.revised_capstone.Adapters;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allem.revised_capstone.Model.ServiceHomeModel;
import com.example.allem.revised_capstone.R;
import com.example.allem.revised_capstone.ServiceProvider.FragmentHomeService;

import java.util.ArrayList;

public class ServiceHomeAdapter extends RecyclerView.Adapter<ServiceHomeAdapter.ViewHolder> {
    private ArrayList<ServiceHomeModel> valueList;
    private ArrayList<ServiceHomeModel> arrayList;
    FragmentActivity context;

    public ServiceHomeAdapter(FragmentActivity ctx, ArrayList<ServiceHomeModel> valueList) {
        this.valueList = valueList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(FragmentHomeService.service_home_list);
        this.context = ctx;

    }

    @Override
    public ServiceHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_home_service, parent, false);
        return new ServiceHomeAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ServiceHomeAdapter.ViewHolder holder, int position) {
        String space = " ";
        holder.tvId.setText(valueList.get(position).getId());
        holder.tvFullName.setText(space + valueList.get(position).getPostedBy());
        holder.tvTitle.setText(space + valueList.get(position).getTitle());
        holder.tvDesc.setText(space + valueList.get(position).getDescription());
        holder.tvReward.setText(space + valueList.get(position).getReward());

    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvTitle, tvDesc, tvReward, tvFullName;
        ImageView iv_prof;

        public ViewHolder(View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.id_Service);
            tvTitle = itemView.findViewById(R.id.titleTv_Service);
            tvDesc = itemView.findViewById(R.id.descriptionTv_Service);
            tvReward = itemView.findViewById(R.id.rewardTv_Service);
            tvFullName = itemView.findViewById(R.id.fullName_Service);

            iv_prof = itemView.findViewById(R.id.iv_profile_Service);

        }
    }
}

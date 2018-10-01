package com.example.allem.revised_capstone.Adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allem.revised_capstone.Model.User_AppointModel;
import com.example.allem.revised_capstone.R;
import com.example.allem.revised_capstone.User.FragmentAppointUser;

import java.util.ArrayList;
import java.util.Locale;

public class User_AppointAdapter extends RecyclerView.Adapter<User_AppointAdapter.ViewHolder> {
    private ArrayList<User_AppointModel> valueList;
    private ArrayList<User_AppointModel> arrayList;
    private FragmentActivity context;


    public User_AppointAdapter(FragmentActivity ctx, ArrayList<User_AppointModel> valueList) {
        this.valueList = valueList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(FragmentAppointUser.user_appointList);
        this.context = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_user_appoint,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull User_AppointAdapter.ViewHolder holder, int position) {
        String space = " ";
       /* String url_image = Constants.BASEURL + "oddjobs/Upload/User/" + valueList.get(position).getHomeOwner() + ".jpg";
        url_image = url_image.replace(" ", "%20");
        Glide.with(context).load(url_image).asBitmap().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(holder.image);*/
        holder.tvFullName.setText(space + valueList.get(position).getFirst() + space + valueList.get(position).getMiddle() + space + valueList.get(position).getLast());
        holder.tvEmail.setText(space + valueList.get(position).getEmail());
        holder.tvContact.setText(space + valueList.get(position).getContact());
        holder.tvRate.setText(space + valueList.get(position).getRatings());
        holder.tvTagging.setText(space + valueList.get(position).getTags());
    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        FragmentAppointUser.user_appointList.clear();
        if (charText.length() == 0) {
            FragmentAppointUser.user_appointList.addAll(arrayList);
        } else {
            for (User_AppointModel wp : arrayList) {
                if (wp.getTags().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getFirst().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getLast().toLowerCase(Locale.getDefault()).contains(charText)) {
                    FragmentAppointUser.user_appointList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName, tvEmail, tvContact, tvRate, tvTagging;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.fullName_User_Appoint);
            tvEmail = itemView.findViewById(R.id.email_user_appoint);
            tvContact = itemView.findViewById(R.id.contact_user_appoint);
            tvRate = itemView.findViewById(R.id.ratings_user_appoint);
            tvTagging = itemView.findViewById(R.id.tag_user_appoint);
        }
    }
}


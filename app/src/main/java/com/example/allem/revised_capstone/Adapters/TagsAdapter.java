package com.example.allem.revised_capstone.Adapters;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.allem.revised_capstone.Model.TagModel;
import com.example.allem.revised_capstone.R;
import com.example.allem.revised_capstone.User.FragmentHomeUser;

import org.angmarch.views.SpinnerTextFormatter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {
    private ArrayList<TagModel> valueList;
    private ArrayList<TagModel> arrayList;
    private FragmentActivity context;

    public TagsAdapter(FragmentActivity ctx, ArrayList<TagModel> valueList) {
        this.valueList = valueList;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(FragmentHomeUser.tagLists);
        this.context = ctx;
    }

    @Override
    public TagsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_tag_lists, parent, false);
        return new TagsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagsAdapter.ViewHolder holder, int position) {
        holder.tvTagName.setText(valueList.get(position).getTags());
    }

    @Override
    public int getItemCount() {
        return valueList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTagName;

        ViewHolder(View itemView) {
            super(itemView);
            tvTagName = itemView.findViewById(R.id.cardview_tv_tag);
        }
    }
}

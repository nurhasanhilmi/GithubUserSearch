package com.hilmi.githubusersearch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hilmi.githubusersearch.R;
import com.hilmi.githubusersearch.model.Following;

import java.util.ArrayList;

public class ListFollowingAdapter extends RecyclerView.Adapter<ListFollowingAdapter.ListFollowingViewHolder> {
    ArrayList<Following> data = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<Following> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListFollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ListFollowingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFollowingViewHolder holder, int position) {
        Following following = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(following.getAvatarUrl())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.ivAvatar);

        holder.tvUsername.setText(following.getLogin());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(data.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ListFollowingViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvUsername;

        public ListFollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.img_search_result_avatar);
            tvUsername = itemView.findViewById(R.id.tv_search_result_name);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Following item);
    }
}

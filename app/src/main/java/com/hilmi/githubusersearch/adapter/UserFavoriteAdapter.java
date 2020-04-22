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
import com.hilmi.githubusersearch.db.User;

import java.util.ArrayList;
import java.util.List;

public class UserFavoriteAdapter extends RecyclerView.Adapter<UserFavoriteAdapter.UserFavoriteViewHolder> {
    private List<User> users = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public UserFavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_favorite, parent, false);
        return new UserFavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFavoriteViewHolder holder, int position) {
        User user = users.get(position);

        Glide.with(holder.itemView.getContext())
                .load(user.avatarUrl)
                .apply(new RequestOptions().override(55, 55))
                .into(holder.ivAvatar);

        holder.tvUsername.setText(user.username);
        holder.tvName.setText(user.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(users.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserFavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName, tvUsername;

        public UserFavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_favorite_avatar);
            tvName = itemView.findViewById(R.id.tv_favorite_name);
            tvUsername = itemView.findViewById(R.id.tv_favorite_username);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(User item);
    }
}

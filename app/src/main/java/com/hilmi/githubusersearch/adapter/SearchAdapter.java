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
import com.hilmi.githubusersearch.model.SearchUserItem;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private ArrayList<SearchUserItem> data = new ArrayList<>();
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<SearchUserItem> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, int position) {
        SearchUserItem item = data.get(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getAvatarUrl())
                .apply(new RequestOptions().override(55, 55))
                .into(holder.imgAvatar);

        holder.tvUsername.setText(item.getLogin());

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

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvUsername;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_search_result_avatar);
            tvUsername = itemView.findViewById(R.id.tv_search_result_name);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(SearchUserItem item);
    }
}

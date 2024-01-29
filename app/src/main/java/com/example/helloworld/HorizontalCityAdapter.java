package com.example.helloworld;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HorizontalCityAdapter extends RecyclerView.Adapter<HorizontalCityAdapter.HorizontalViewHolder> {

    private List<City> items;


    public HorizontalCityAdapter(List<City> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_city_item, parent, false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {
        City item = items.get(position);
        holder.imageView.setImageResource(item.getImageResource());
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.infoButton.setOnClickListener(v -> {
            Log.d("ButtonClick", "Button clicked for item: " + item.getTitle());
            Intent intent = new Intent(v.getContext(), AttractionActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
        ImageButton infoButton;

        HorizontalViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.horizontal_item_image);
            titleTextView = itemView.findViewById(R.id.horizontal_item_title);
            descriptionTextView = itemView.findViewById(R.id.horizontal_item_description);
            infoButton = itemView.findViewById(R.id.horizontal_item_button);
        }
    }

    public void setNewItems(List<City> newData) {
        this.items = newData;
        notifyDataSetChanged();
    }
}


package com.example.helloworld;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class VerticalCityAdapter extends RecyclerView.Adapter<VerticalCityAdapter.VerticalViewHolder> {

    private List<City> items;

    public VerticalCityAdapter(List<City> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_city_item, parent, false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {
        City item = items.get(position);
        holder.imageView.setImageResource(item.getImageResource());
        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
        holder.infoButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), StationActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VerticalViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
        ImageButton infoButton;

        VerticalViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.vertical_item_image);
            titleTextView = itemView.findViewById(R.id.vertical_item_title);
            descriptionTextView = itemView.findViewById(R.id.vertical_item_description);
            infoButton = itemView.findViewById(R.id.vertical_item_button);
        }
    }
}

package com.example.helloworld;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.Button;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    private List<Attraction> items;

    public void setItems(List<Attraction> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_item, parent, false);
        return new ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Attraction item = items.get(position);
        holder.imageView.setImageResource(item.getImage());
        holder.textTitle.setText(item.getTitle());
        holder.textDescription.setText(item.getDescription());

        // Set click listener for the "View Details" button
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start DetailsActivity and pass data
                Context context = v.getContext();
                Intent intent = new Intent(context, AttractionDetailsActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("Image", item.getImage());
                intent.putExtra("Title", item.getTitle());
                intent.putExtra("Description", item.getDescription());
                intent.putExtra("Rating", item.getRating());
                intent.putExtra("Location", item.getLocation());
                intent.putExtra("Distance", item.getDistance());
                intent.putExtra("Website", item.getWebsite());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textTitle;
        TextView textDescription;
        Button btnViewDetails;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);
        }
    }
}

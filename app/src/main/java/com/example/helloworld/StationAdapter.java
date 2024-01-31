package com.example.helloworld;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.StationViewHolder> {

    private List<Station> stationList;

    public StationAdapter(List<Station> stationList) {
        this.stationList = stationList;
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.station_item, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Station station = stationList.get(position);
        holder.bind(station);

        holder.stationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isExpanded = holder.detailsLayout.getVisibility() == View.VISIBLE;
                holder.detailsLayout.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
                // Adjust arrow indicator here if you have one
            }
        });
    }


    // Method to update the data in the adapter
    public void updateData(List<Station> newStationList) {
        this.stationList = newStationList;
        notifyDataSetChanged(); // Notify the adapter to refresh the list
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public class StationViewHolder extends RecyclerView.ViewHolder {
        TextView stationName;
        TextView stationDistance;
        ImageView imageView1, imageView2, imageView3;
        LinearLayout detailsLayout;

        public StationViewHolder(View itemView) {
            super(itemView);
            stationName = itemView.findViewById(R.id.textViewStationName);
            stationDistance = itemView.findViewById(R.id.textViewStationDistance);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);
            imageView3 = itemView.findViewById(R.id.imageView3);
            detailsLayout = itemView.findViewById(R.id.detailsLayout);

            // Set an OnClickListener to toggle the visibility of the detailsLayout
            stationName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toggle visibility of detailsLayout
                    detailsLayout.setVisibility(detailsLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                    // Change the arrow drawable based on the visibility
                    // Assuming you have an arrow indicator in your layout
                    // You can set or change the drawable of the arrow here if needed
                }
            });
        }

        public void bind(Station station) {
            stationName.setText(station.getName());
            stationDistance.setText(String.join(", ", station.getTimes()));

            // Here you would set the images for the ImageViews based on the station data
            // For example, you might have different images for different types of buses or trains
            imageView1.setImageResource(station.getImageResourceId()); // Placeholder for actual image setting logic
            imageView2.setImageResource(station.getImageResourceId()); // Placeholder for actual image setting logic
            imageView3.setImageResource(station.getImageResourceId()); // Placeholder for actual image setting logic
        }
    }
}

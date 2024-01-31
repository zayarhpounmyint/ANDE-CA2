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
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public void updateData(List<Station> newStationList) {
        this.stationList = newStationList;
        notifyDataSetChanged();
    }

    public class StationViewHolder extends RecyclerView.ViewHolder {
        TextView stationName;
        ImageView[] imageViews = new ImageView[3];
        TextView[] distanceViews = new TextView[3];
        LinearLayout detailsLayout; // This will contain the images and distances

        public StationViewHolder(View itemView) {
            super(itemView);
            stationName = itemView.findViewById(R.id.textViewStationName);
            detailsLayout = itemView.findViewById(R.id.detailsLayout); // Layout to toggle visibility
            imageViews[0] = itemView.findViewById(R.id.imageView1);
            imageViews[1] = itemView.findViewById(R.id.imageView2);
            imageViews[2] = itemView.findViewById(R.id.imageView3);
            distanceViews[0] = itemView.findViewById(R.id.textViewDistance1);
            distanceViews[1] = itemView.findViewById(R.id.textViewDistance2);
            distanceViews[2] = itemView.findViewById(R.id.textViewDistance3);

            stationName.setOnClickListener(v -> {
                // Toggle the visibility of the detailsLayout
                detailsLayout.setVisibility(detailsLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            });
        }

        public void bind(Station station) {
            stationName.setText(station.getName());

            List<Integer> imageResourceIds = station.getImageResourceIds();
            List<String> times = station.getTimes();

            for (int i = 0; i < imageViews.length; i++) {
                if (i < imageResourceIds.size()) {
                    imageViews[i].setImageResource(imageResourceIds.get(i));
                }
                if (i < times.size()) {
                    distanceViews[i].setText(times.get(i));
                }
            }
        }
    }
}

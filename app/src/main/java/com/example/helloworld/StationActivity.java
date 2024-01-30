package com.example.helloworld;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StationAdapter adapter;
    private List<Station> busStations;
    private List<Station> trainStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus);

        // Initialize the RecyclerView with data
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create lists of stations for buses and trains
        busStations = createBusStationData();
        trainStations = createTrainStationData();

        // Start with displaying train data by default
        adapter = new StationAdapter(busStations);
        recyclerView.setAdapter(adapter);

        // Set up button click listeners to switch between Bus and Train views
        Button busNav = findViewById(R.id.busNav);
        Button trainNav = findViewById(R.id.trainNav);
        busNav.setOnClickListener(v -> showBusView());
        trainNav.setOnClickListener(v -> showTrainView());
    }

    private void showBusView() {
        adapter.updateData(busStations);
    }

    private void showTrainView() {
        adapter.updateData(trainStations);
    }

    private List<Station> createBusStationData() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("Central Bus Station", R.drawable.bus, Arrays.asList("3 min", "7 min", "14 min")));
        stations.add(new Station("Downtown Bus Hub", R.drawable.bus, Arrays.asList("6 min", "12 min", "24 min")));
        // Add more bus stations as needed
        return stations;
    }

    // Helper method to create dummy train station data
    private List<Station> createTrainStationData() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("Tokyo Station", R.drawable.train, Arrays.asList("5 min", "10 min", "15 min")));
        stations.add(new Station("Shibuya Station", R.drawable.train, Arrays.asList("2 min", "8 min", "12 min")));
        // Add more train stations as needed
        return stations;
    }
}


package com.example.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StationFragment extends Fragment {

    private RecyclerView recyclerView;
    private StationAdapter adapter;
    private List<Station> busStations;
    private List<Station> trainStations;

    public StationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.transport_activity, container, false);

        // Initialize the RecyclerView with data
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create lists of stations for buses and trains
        busStations = createBusStationData();
        trainStations = createTrainStationData();

        // Start with displaying train data by default
        adapter = new StationAdapter(busStations);
        recyclerView.setAdapter(adapter);

        // Set up button click listeners to switch between Bus and Train views
        Button busNav = view.findViewById(R.id.busNav);
        Button trainNav = view.findViewById(R.id.trainNav);
        busNav.setOnClickListener(v -> showBusView());
        trainNav.setOnClickListener(v -> showTrainView());

        return view;
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
        return stations;
    }

    // Helper method to create dummy train station data
    private List<Station> createTrainStationData() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station("Tokyo Station", R.drawable.train, Arrays.asList("5 min", "10 min", "15 min")));
        stations.add(new Station("Shibuya Station", R.drawable.train, Arrays.asList("2 min", "8 min", "12 min")));
        return stations;
    }
}

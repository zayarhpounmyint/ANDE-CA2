package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;


public class AttractionDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_details_activity);

        Button backButton = findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the activity you want to go back to
                Intent intent = new Intent(AttractionDetailsActivity.this, AttractionActivity.class);
                startActivity(intent);
            }
        });



        // Get data from the intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);


        DatabaseHandler db = new DatabaseHandler(this);
        Attraction attractionDetails = db.getAttraction(id);
        int image = attractionDetails.getImage();
        String title = attractionDetails.getTitle();
        String desc = attractionDetails.getDescription();
        Double rating = attractionDetails.getRating();
        String location = attractionDetails.getLocation();
        String website = attractionDetails.getWebsite();



        // Set data to views on the details page
        ImageView attractionImg = findViewById(R.id.attractionImg);
        attractionImg.setImageResource(image);

        TextView textDetailsTitle = findViewById(R.id.textDetailsTitle);
        textDetailsTitle.setText(title);

        TextView textDetailsDesc = findViewById(R.id.textDetailsDesc);
        textDetailsDesc.setText(desc);

        // Find the RatingBar view
        RatingBar ratingBar = findViewById(R.id.ratingBar);

        // Get the rating from your attractionDetails object
        float starRating = rating.floatValue(); // Convert the Double rating to float

        // Set the rating to the RatingBar
        ratingBar.setRating(starRating);

        TextView textDetailsLocation = findViewById(R.id.textDetailsLocation);
        textDetailsLocation.setText(location);

        TextView textDetailsWebsite = findViewById(R.id.textDetailsWebsite);
        textDetailsWebsite.setText(website);


        //add to list button
        Button addToListBtn = findViewById(R.id.addToPlanBtn);

        // Set OnClickListener to the button
        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start NewActivity
                Intent intent = new Intent(AttractionDetailsActivity.this, AddToPlanActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        int attractionId = intent.getIntExtra("id", 0);

        // Get the EditText and Button from the layout
        Button commentButton = findViewById(R.id.commentBtn);


        // Set OnClickListener for the comment button
        commentButton.setOnClickListener(v -> {
            Intent intent1 = new Intent(AttractionDetailsActivity.this, CommentActivity.class);
            intent1.putExtra("id", attractionId);
            startActivity(intent1);
        });

    }
}
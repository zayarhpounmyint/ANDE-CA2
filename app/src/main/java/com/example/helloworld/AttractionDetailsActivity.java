package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import java.util.List;


public class AttractionDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_details);

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
//        int image = intent.getIntExtra("Image",0);
//        String title = intent.getStringExtra("Title");
//        String desc = intent.getStringExtra("Description");
//        Double rating = intent.getDoubleExtra("Rating",0);
//        String location = intent.getStringExtra("Location");
//        String distance = intent.getStringExtra("Distance");
//        String website = intent.getStringExtra("Website");

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

        TextView textDetailsRating = findViewById(R.id.textDetailsRating);
        textDetailsRating.setText(rating.toString());

        TextView textDetailsLocation = findViewById(R.id.textDetailsLocation);
        textDetailsLocation.setText(location);

        TextView textDetailsWebsite = findViewById(R.id.textDetailsWebsite);
        textDetailsWebsite.setText(website);


        //add to list button
        Button addToListBtn = findViewById(R.id.addToListBtn);

        // Set OnClickListener to the button
        addToListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start NewActivity
                Intent intent = new Intent(AttractionDetailsActivity.this, addToPlanActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        int attractionId = intent.getIntExtra("id", 0);

        // Get the EditText and Button from the layout
        EditText commentInput = findViewById(R.id.commentInput);
        Button commentButton = findViewById(R.id.commentBtn);

        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));


        List<Comment> comments = db.getCommentsForAttraction(attractionId); // Make sure this method is implemented in your DatabaseHandler

        commentAdapter = new CommentAdapter(comments);
        recyclerViewComments.setAdapter(commentAdapter);

        // Set OnClickListener for the comment button
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the comment text from the EditText
                String commentText = commentInput.getText().toString();

                // Check if the comment is not empty
                if (!commentText.isEmpty()) {
                    // Create a Comment object
                    Comment comment = new Comment();
                    comment.setAttractionId(attractionId);
                    comment.setComment(commentText);

                    // Save the comment to the database
                    DatabaseHandler db = new DatabaseHandler(AttractionDetailsActivity.this);
                    db.addComment(comment);

                    // Optionally clear the EditText or provide feedback to the user
                    commentInput.setText("");
                    // Display a toast message, etc.
                } else {
                    // Handle the case where the comment is empty
                    // E.g., display a Toast message to the user
                }
            }
        });
    }
}
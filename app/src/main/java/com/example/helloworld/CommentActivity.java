package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private EditText commentInput;
    private Button submitCommentBtn;
    private int attractionId;
    private FirebaseFirestore db;
    private SessionManager sessionManager;
    private String currentUsername; // Variable to store the current user's username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize session manager
        sessionManager = new SessionManager(this);

        Button backButton = findViewById(R.id.backBtn);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CommentActivity.this, AttractionDetailsActivity.class);
            startActivity(intent);
        });

        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        commentInput = findViewById(R.id.commentInput);
        submitCommentBtn = findViewById(R.id.commentBtn);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        attractionId = getIntent().getIntExtra("id", 0);
        DatabaseHandler localDb = new DatabaseHandler(this);
        List<Comment> comments = localDb.getCommentsForAttraction(attractionId);
        commentAdapter = new CommentAdapter(comments);
        recyclerViewComments.setAdapter(commentAdapter);

        // Fetch the username
        fetchUsername();

        submitCommentBtn.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString();
            if (!commentText.isEmpty() && currentUsername != null && !currentUsername.isEmpty()) {
                Comment comment = new Comment(attractionId, currentUsername, commentText);
                localDb.addComment(comment);
                commentInput.setText("");
                comments.add(comment);
                commentAdapter.notifyItemInserted(comments.size() - 1);
            }
        });
    }

    private void fetchUsername() {
        String userId = sessionManager.getUserId();

        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            // Set username
                            currentUsername = document.getString("username");
                        } else {
                            Log.d("CommentActivity", "No such document");
                        }
                    } else {
                        Log.d("CommentActivity", "get failed with ", task.getException());
                    }
                });
    }
}

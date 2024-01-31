package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;
    private EditText commentInput;
    private Button submitCommentBtn;
    private int attractionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);

        Button backButton = findViewById(R.id.backBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the activity you want to go back to
                Intent intent = new Intent(CommentActivity.this, AttractionDetailsActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        commentInput = findViewById(R.id.commentInput);
        submitCommentBtn = findViewById(R.id.commentBtn);

        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));

        attractionId = getIntent().getIntExtra("id", 0);
        DatabaseHandler db = new DatabaseHandler(this);
        List<Comment> comments = db.getCommentsForAttraction(attractionId);
        commentAdapter = new CommentAdapter(comments);
        recyclerViewComments.setAdapter(commentAdapter);

        submitCommentBtn.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString();
            if (!commentText.isEmpty()) {
                Comment comment = new Comment();
                comment.setAttractionId(attractionId);
                comment.setComment(commentText);
                db.addComment(comment);
                commentInput.setText("");
                comments.add(comment);
                commentAdapter.notifyItemInserted(comments.size() - 1);
            }
        });
    }
}

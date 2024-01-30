package com.example.helloworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private EditText editUserNameEditText, editEmailEditText, editPhoneEditText;
    private Button saveButton;
    private FirebaseFirestore db;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize session manager
        SessionManager sessionManager = new SessionManager(requireContext());
        userId = sessionManager.getUserId();

        // Initialize UI elements
        editUserNameEditText = view.findViewById(R.id.editUserNameEditText);
        editEmailEditText = view.findViewById(R.id.editEmailEditText);
        editPhoneEditText = view.findViewById(R.id.editPhoneEditText);
        saveButton = view.findViewById(R.id.saveButton);

        // Fetch and display user data when the fragment is created
        fetchAndDisplayUserData();

        // Set an OnClickListener for the Save button to update user information
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get edited information
                String editedUserName = editUserNameEditText.getText().toString();
                String editedEmail = editEmailEditText.getText().toString();
                String editedPhone = editPhoneEditText.getText().toString();

                // Update user information in Firestore
                updateUserDataInFirestore(editedUserName, editedEmail, editedPhone);

                // Update user information in Firebase Authentication
                updateAuthenticationEmail(editedEmail);
            }
        });

        return view;
    }

    private void fetchAndDisplayUserData() {
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Get user data
                                String userName = document.getString("username");
                                String email = document.getString("useremail");
                                int phone = 0;

                                Object phoneObj = document.get("userphone");
                                if (phoneObj instanceof Long) {
                                    phone = ((Long) phoneObj).intValue();
                                } else if (phoneObj instanceof Integer) {
                                    phone = (Integer) phoneObj;
                                }

                                // Display user data in EditText fields
                                editUserNameEditText.setText(userName);
                                editEmailEditText.setText(email);
                                editPhoneEditText.setText(String.valueOf(phone));
                            }
                        } else {
                            // Handle error
                        }
                    }
                });
    }

    private void updateUserDataInFirestore(String editedUserName, String editedEmail, String editedPhone) {
        // Update user information in Firestore
        db.collection("users").document(userId)
                .update("username", editedUserName,
                        "useremail", editedEmail,
                        "userphone", editedPhone)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Update successful
                            // You can show a success message or perform other actions
                        } else {
                            // Handle error
                        }
                    }
                });
    }

    private void updateAuthenticationEmail(String newEmail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Email update successful
                            } else {
                                // Handle email update failure
                            }
                        }
                    });
        }
    }
}

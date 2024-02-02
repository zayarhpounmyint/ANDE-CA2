package com.example.helloworld;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.bumptech.glide.Glide;

public class ProfileFragment extends Fragment {

    private EditText editUserNameEditText, editEmailEditText, editPhoneEditText;
    private Button saveButton;
    private FirebaseFirestore db;
    private String userId;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ImageView ViewProfileImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize session manager
        SessionManager sessionManager = new SessionManager(requireContext());
        userId = sessionManager.getUserId();

        ViewProfileImage = view.findViewById(R.id.imgViewProfile);


        // Initialize UI elements
        editUserNameEditText = view.findViewById(R.id.editUserNameEditText);
        editEmailEditText = view.findViewById(R.id.editEmailEditText);
        editPhoneEditText = view.findViewById(R.id.editPhoneEditText);
        saveButton = view.findViewById(R.id.saveButton);

        // Fetch and display user data when the fragment is created
        fetchAndDisplayUserData();

        // Set OnClickListener for the button
        Button uploadPhotoButton = view.findViewById(R.id.btnUploadProfile);
        uploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooserAndUpload();
            }
        });


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

            }
        });

        // Set OnClickListener for the logout button
        Button logoutButton = view.findViewById(R.id.btnLogOut);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to log out?");

                // Add the buttons
                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Logout button
                        FirebaseAuth.getInstance().signOut();
                        // Navigate to the login screen
                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                        startActivity(intent);
                        requireActivity().finish(); // Finish current activity to prevent going back to profile screen
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
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
                                String phone = document.getString("userphone");
                                String imageUrl;
                                if (document.getString("userimageurl") == null ){
                                    imageUrl = "https://firebasestorage.googleapis.com/v0/b/adne-steven.appspot.com/o/profileImages%2Fdefaultprofile.jpeg?alt=media&token=955ba03c-4bdb-4d23-bc1e-ce6dfebfcedb";
                                } else {
                                    imageUrl = document.getString("userimageurl");
                                }

                                // Debugging log statements
                                Log.d("ProfileFragment", "Fetched phone number: " + phone);

                                // Display user data in EditText fields
                                editUserNameEditText.setText(userName);
                                editEmailEditText.setText(email);
                                editPhoneEditText.setText(phone);

                                // Load profile image using Glide or Picasso
                                if (imageUrl != null) {
                                    Glide.with(requireContext())
                                            .load(imageUrl)
                                            .placeholder(R.drawable.kyoto) // Placeholder image while loading
                                            .error(R.drawable.splogo) // Error image if loading fails
                                            .into(ViewProfileImage);
                                } else {
                                    // If no image URL is provided, you can set a default image here
                                    ViewProfileImage.setImageResource(R.drawable.paris);
                                }

                            } else {
                                Log.d("ProfileFragment", "Document does not exist");
                            }
                        } else {
                            Log.d("ProfileFragment", "Error getting documents: " + task.getException());
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

    // Method to handle opening file chooser and uploading image
    public void openFileChooserAndUpload() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult to handle result from file chooser
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            Log.d("ImagePicker", String.valueOf(requestCode));
            Log.d("ImagePicker", String.valueOf(resultCode));
            Log.d("Data" , String.valueOf(data.getData()));

            if (resultCode == -1 && data != null && data.getData() != null) {
                mImageUri = data.getData(); // Assign the selected image URI to mImageUri
                Log.d("ImagePicker", "Selected Image URI: " + mImageUri.toString());
                uploadImage();
            } else {
                // Handle if no image is selected or selection canceled
                Toast.makeText(requireContext(), "Image selection canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to upload the selected image to Firebase Storage and update Firestore
    private void uploadImage() {
        if (mImageUri != null) {
            String folderPath = "profileImages/" + userId + "/";
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(folderPath);
            UploadTask uploadTask = storageRef.putFile(mImageUri);
            uploadTask.addOnSuccessListener(taskSnapshot -> {
                // Image uploaded successfully, now retrieve the download URL
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();
                    db.collection("users").document(userId)
                            .update("userimageurl", imageUrl)
                            .addOnSuccessListener(aVoid -> {
                                Glide.with(requireContext())
                                        .load(imageUrl)
                                        .placeholder(R.drawable.profile_default)
                                        .error(R.drawable.splogo)
                                        .into(ViewProfileImage);
                            })
                            .addOnFailureListener(e -> {
                                Log.e("UploadImage", "Failed to update Firestore document: " + e.getMessage());
                            });
                });
            }).addOnFailureListener(e -> {
                Log.e("UploadImage", "Failed to upload image: " + e.getMessage());
            });
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

}

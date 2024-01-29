package com.example.helloworld;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class addToPlanActivity extends AppCompatActivity {

    Button btnStartDate, btnEndDate, btnStartTime, btnEndTime, btnConfirmPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_to_plan);

        Button cancelButton = findViewById(R.id.cancelBtn);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);

        DatabaseHandler db = new DatabaseHandler(this);
        Attraction attractionAdded = db.getAttraction(id);
        String title = attractionAdded.getTitle();
        String location = attractionAdded.getLocation();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addToPlanActivity.this, AttractionDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });



        // Initialize buttons
        btnStartDate = findViewById(R.id.btnStartDatePicker);
        btnEndDate = findViewById(R.id.btnEndDatePicker);
        btnStartTime = findViewById(R.id.btnStartTimePicker);
        btnEndTime = findViewById(R.id.btnEndTimePicker);
        btnConfirmPlan = findViewById(R.id.btnConfirmPlan); // Initialize the confirm button

        // Set listeners for the date and time pickers
        setDateListener(btnStartDate);
        setDateListener(btnEndDate);
        setTimeListener(btnStartTime);
        setTimeListener(btnEndTime);

        // Set listener for the confirm button
        btnConfirmPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if all date and time have been selected
                if (btnStartDate.getText().equals("Select Date")
                        || btnEndDate.getText().equals("Select Date")
                        || btnStartTime.getText().equals("Select Time")
                        || btnEndTime.getText().equals("Select Time")) {
                    // Alert the user
                    new AlertDialog.Builder(addToPlanActivity.this)
                            .setTitle("Incomplete Information")
                            .setMessage("Please select all dates and times before confirming.")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    // Proceed with confirming plan
                    // Your code for what happens after all selections are confirmed
                }
            }
        });
    }

    private void setDateListener(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(addToPlanActivity.this,
                        (view, year1, monthOfYear, dayOfMonth) -> {
                            String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1;
                            button.setText(selectedDate);
                            Log.d("DatePicker", button.getId() == R.id.btnStartDatePicker ? "From Date: " : "To Date: " + selectedDate);
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
    }

    private void setTimeListener(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(addToPlanActivity.this,
                        (view, hourOfDay, minute1) -> {
                            String selectedTime = hourOfDay + ":" + minute1;
                            button.setText(selectedTime);
                            Log.d("TimePicker", button.getId() == R.id.btnStartTimePicker ? "From Time: " : "To Time: " + selectedTime);
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }
}

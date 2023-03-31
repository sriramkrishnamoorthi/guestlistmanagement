package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;


public class GuestActivity extends AppCompatActivity {

    private ArrayList<Guest> guestList;
    private GuestAdapter guestAdapter;
    private EditText guestNameEditText;
    private EditText guestEmailEditText;
    private Button addGuestButton;

    private TextView yesCountTextView;
    private TextView noCountTextView;
    private TextView maybeCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Guest Activity");

        // Load the guest list data from SharedPreferences
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String guestListJson = sharedPreferences.getString("guestList", null);

        if (guestListJson != null) {
            // Convert the JSON string to a list of Guest objects
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Guest>>() {
            }.getType();
            guestList = gson.fromJson(guestListJson, type);
        }

        // If the guest list is null, create a new list
        if (guestList == null) {
            guestList = new ArrayList<>();
        }

        // Initialize the guest list and adapter
        // Initialize the guest list and adapter
        guestAdapter = new GuestAdapter(this, guestList, new GuestAdapter.ResponseListener() {
            @Override
            public void onResponseChanged() {
                // Update the response counts when a guest response changes
                updateResponseCounts();
            }
        });



        // Set the adapter for the guest list ListView
        ListView guestListView = findViewById(R.id.guest_list_view);
        guestListView.setAdapter(guestAdapter);

        // Initialize the response count TextViews
        yesCountTextView = findViewById(R.id.yes_count_textview);
        noCountTextView = findViewById(R.id.no_count_textview);
        maybeCountTextView = findViewById(R.id.maybe_count_textview);

        // Update the response counts initially
        updateResponseCounts();

        // Get references to the EditText fields and "Add Guest" button
        guestNameEditText = findViewById(R.id.guest_name_edittext);
        guestEmailEditText = findViewById(R.id.guest_email_edittext);
        addGuestButton = findViewById(R.id.add_guest_button);

        // Set an onClickListener for the "Add Guest" button
        addGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the guest name and email from the EditText fields
                String name = guestNameEditText.getText().toString();
                String email = guestEmailEditText.getText().toString();

                // Validate the email
                if (!isValidEmail(email)) {
                    Toast.makeText(GuestActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new guest object with a default status and add it to the list
                Guest guest = new Guest(name, email, "Yes");
                guestList.add(guest);

                // Notify the adapter that the data has changed
                guestAdapter.notifyDataSetChanged();

                // Clear the EditText fields for the next guest
                guestNameEditText.setText("");
                guestEmailEditText.setText("");

                // Update the response counts
                updateResponseCounts();
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();

        // Save the guest list data to SharedPreferences
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Convert the guest list to a JSON string and save it to SharedPreferences
        Gson gson = new Gson();
        String guestListJson = gson.toJson(guestList);
        editor.putString("guestList", guestListJson);
        editor.apply();
    }

    private void updateResponseCounts() {
        int yesCount = 0;
        int noCount = 0;
        int maybeCount = 0;

        // Count the number of guests who responded with Yes, No, or Maybe
        for (Guest guest : guestList) {
            String response = guest.getResponse();
            if (response != null) {
                if (response.equals("Yes")) {
                    yesCount++;
                } else if (response.equals("No")) {
                    noCount++;
                } else if (response.equals("Maybe")) {
                    maybeCount++;
                }
            }
        }

        // Update the values of the TextViews for the response counts
        yesCountTextView.setText("Yes: " + yesCount);
        noCountTextView.setText("No: " + noCount);
        maybeCountTextView.setText("Maybe: " + maybeCount);
    }

    private boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
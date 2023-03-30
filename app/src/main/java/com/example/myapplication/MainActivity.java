package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Guest> guestList;
    private GuestAdapter guestAdapter;
    private EditText guestNameEditText;
    private EditText guestEmailEditText;
    private Button addGuestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the guest list and adapter
        guestList = new ArrayList<>();
        guestAdapter = new GuestAdapter(this, guestList);

        // Get references to the EditText fields and "Add Guest" button
        guestNameEditText = findViewById(R.id.guest_name_edittext);
        guestEmailEditText = findViewById(R.id.guest_email_edittext);
        addGuestButton = findViewById(R.id.add_guest_button);

        // Set the adapter for the guest list ListView
        ListView guestListView = findViewById(R.id.guest_list_view);
        guestListView.setAdapter(guestAdapter);

        // Set an onClickListener for the "Add Guest" button
        addGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the guest name and email from the EditText fields
                String name = guestNameEditText.getText().toString();
                String email = guestEmailEditText.getText().toString();

                // Create a new guest object and add it to the list
                Guest guest = new Guest(name, email);
                guestList.add(guest);

                // Notify the adapter that the data has changed
                guestAdapter.notifyDataSetChanged();

                // Clear the EditText fields for the next guest
                guestNameEditText.setText("");
                guestEmailEditText.setText("");
            }
        });
    }
}

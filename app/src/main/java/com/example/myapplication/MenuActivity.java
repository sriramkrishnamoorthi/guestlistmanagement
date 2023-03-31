package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    private Button guestListManagementButton;
    private Button cateringManagementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        guestListManagementButton = findViewById(R.id.guest_list_management_button);
        guestListManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the MainActivity (Guest List Management)
                Intent intent = new Intent(MenuActivity.this, GuestActivity.class);
                startActivity(intent);
            }
        });

        cateringManagementButton = findViewById(R.id.catering_management_button);
        cateringManagementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CateringActivity
                Intent intent = new Intent(MenuActivity.this, CateringActivity.class);
                startActivity(intent);
            }
        });
    }
}

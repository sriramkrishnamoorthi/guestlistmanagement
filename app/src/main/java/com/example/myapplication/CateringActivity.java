    package com.example.myapplication;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.Toast;

    import java.util.ArrayList;
    import java.util.List;

    import android.content.DialogInterface;
    import android.widget.EditText;
    import android.widget.LinearLayout;
    import androidx.appcompat.app.AlertDialog;

    import android.content.SharedPreferences;
    import com.google.gson.Gson;
    import com.google.gson.reflect.TypeToken;
    import java.lang.reflect.Type;
    import java.util.HashSet;
    import java.util.Set;
    import androidx.appcompat.widget.Toolbar;
    import android.widget.TextView;


    public class CateringActivity extends AppCompatActivity implements CateringAdapter.OnCateringItemClickListener {

        private RecyclerView recyclerView;
        private CateringAdapter cateringAdapter;
        private List<CateringItem> cateringItems;
        private Button addCateringItemButton;

        private TextView totalChargesTextView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_catering);
            recyclerView = findViewById(R.id.catering_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Catering" );
            cateringItems = new ArrayList<>(); // Initialize with some sample data if needed
            loadCateringItems();

            cateringAdapter = new CateringAdapter(this, cateringItems, this);
            recyclerView.setAdapter(cateringAdapter);
            totalChargesTextView = findViewById(R.id.total_charges_text_view);
            updateTotalCharges(); // Add this line to update the total charges after loading the items
            addCateringItemButton = findViewById(R.id.add_catering_item_button);
            addCateringItemButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an AlertDialog to input the details for the new item
                    AlertDialog.Builder builder = new AlertDialog.Builder(CateringActivity.this);
                    builder.setTitle("Add Catering Item");

                    // Set up the input fields
                    LinearLayout layout = new LinearLayout(CateringActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    final EditText foodItemInput = new EditText(CateringActivity.this);
                    foodItemInput.setHint("Food Item");
                    final EditText catererInput = new EditText(CateringActivity.this);
                    catererInput.setHint("Caterer");
                    final EditText chargesInput = new EditText(CateringActivity.this);
                    chargesInput.setHint("Charges");

                    layout.addView(foodItemInput);
                    layout.addView(catererInput);
                    layout.addView(chargesInput);
                    builder.setView(layout);

                    // Set up the buttons
                    builder.setPositiveButton("Add", null);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    // Add a custom click listener to the positive button
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String foodItem = foodItemInput.getText().toString();
                            String caterer = catererInput.getText().toString();
                            String chargesString = chargesInput.getText().toString();

                            if (foodItem.isEmpty() || caterer.isEmpty() || chargesString.isEmpty()) {
                                Toast.makeText(CateringActivity.this, "Please fill in all fields: Food Item, Caterer, and Charges.", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    double charges = Double.parseDouble(chargesString);

                                    // Add the new catering item and update the list
                                    cateringItems.add(new CateringItem(foodItem, caterer, charges));
                                    cateringAdapter.notifyDataSetChanged();

                                    saveCateringItems();

                                    alertDialog.dismiss(); // Dismiss the dialog after a successful addition
                                } catch (NumberFormatException e) {
                                    Toast.makeText(CateringActivity.this, "Invalid input for Charges. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });

                }
            });


        }

        @Override
        public void onItemClick(int position) {
            // Remove the catering item at the given position
            CateringItem removedItem = cateringItems.remove(position);
            cateringAdapter.notifyDataSetChanged();

            // Show a toast to confirm deletion
            String message = String.format("Removed: %s by %s", removedItem.getFoodItemName(), removedItem.getCatererName());
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            saveCateringItems();
            updateTotalCharges(); // Call the updateTotalCharges method after removing an item
        }


        private void loadCateringItems() {
            SharedPreferences sharedPreferences = getSharedPreferences("CateringItems", MODE_PRIVATE);
            String json = sharedPreferences.getString("cateringItems", null);
            Type type = new TypeToken<List<CateringItem>>() {
            }.getType();
            cateringItems = new Gson().fromJson(json, type);

            if (cateringItems == null) {
                cateringItems = new ArrayList<>();
            }
        }

        private void saveCateringItems() {
            SharedPreferences sharedPreferences = getSharedPreferences("CateringItems", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String json = new Gson().toJson(cateringItems);
            editor.putString("cateringItems", json);
            editor.apply();
        }

        private void updateTotalCharges() {
            double totalCharges = 0;
            for (CateringItem item : cateringItems) {
                totalCharges += item.getCatererCharge();
            }
            totalChargesTextView.setText(String.format("Total Charges: $%.2f", totalCharges));
        }


        private void addCateringItem() {
            // Create an AlertDialog to input the details for the new item
            AlertDialog.Builder builder = new AlertDialog.Builder(CateringActivity.this);
            builder.setTitle("Add Catering Item");

            // Set up the input fields
            LinearLayout layout = new LinearLayout(CateringActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            final EditText foodItemInput = new EditText(CateringActivity.this);
            foodItemInput.setHint("Food Item");
            final EditText catererInput = new EditText(CateringActivity.this);
            catererInput.setHint("Caterer");
            final EditText chargesInput = new EditText(CateringActivity.this);
            chargesInput.setHint("Charges");

            layout.addView(foodItemInput);
            layout.addView(catererInput);
            layout.addView(chargesInput);
            builder.setView(layout);

            // Set up the buttons
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String foodItem = foodItemInput.getText().toString();
                    String caterer = catererInput.getText().toString();
                    String chargesString = chargesInput.getText().toString();

                    if (foodItem.isEmpty() || caterer.isEmpty() || chargesString.isEmpty()) {
                        Toast.makeText(CateringActivity.this, "Please fill in all fields: Food Item, Caterer, and Charges.", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            double charges = Double.parseDouble(chargesString);

                            // Add the new catering item and update the list
                            cateringItems.add(new CateringItem(foodItem, caterer, charges));
                            cateringAdapter.notifyDataSetChanged();

                            saveCateringItems();
                            updateTotalCharges(); // Call the updateTotalCharges method after adding a new item

                            dialog.dismiss(); // Dismiss the dialog after a successful addition
                        } catch (NumberFormatException e) {
                            Toast.makeText(CateringActivity.this, "Invalid input for Charges. Please enter a valid number.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
    package com.example.myapplication;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.Spinner;
    import android.widget.TextView;
    import java.util.ArrayList;
    import android.widget.AdapterView;

    import android.content.ClipData;
    import android.content.ClipboardManager;
    import android.widget.Toast;
    import android.widget.ImageView;



    public class GuestAdapter extends ArrayAdapter<Guest> {

        private Context context;
        private ArrayList<Guest> guestList;
        private ResponseListener responseListener; // define responseListener variable

        public GuestAdapter(Context context, ArrayList<Guest> guestList, ResponseListener responseListener) {
            super(context, 0, guestList);
            this.context = context;
            this.guestList = guestList;
            this.responseListener = responseListener; // assign responseListener parameter to variable
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.guest_list_item, parent, false);
            }

            // Get the guest at this position
            Guest guest = getItem(position);

            // Set the guest name and email in the TextView fields
            TextView guestNameTextView = convertView.findViewById(R.id.guest_name_textview);
            TextView guestEmailTextView = convertView.findViewById(R.id.guest_email_textview);
            guestNameTextView.setText(guest.getName());
            guestEmailTextView.setText(guest.getEmail());

            // Initialize the Spinner
            Spinner guestStatusSpinner = convertView.findViewById(R.id.guest_status_spinner);
            initializeSpinner(guestStatusSpinner, guest);

            // Set an OnClickListener for the "Delete" button
            ImageView deleteGuestButton = convertView.findViewById(R.id.delete_guest_button);

            deleteGuestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guestList.remove(position);
                    notifyDataSetChanged();
                    responseListener.onResponseChanged(); // Notify listener of response change
                }
            });

            // Set an OnClickListener for the email address
            guestEmailTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the ClipboardManager system service
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

                    // Copy the email address to the clipboard
                    ClipData clip = ClipData.newPlainText("Guest Email", guest.getEmail());
                    clipboard.setPrimaryClip(clip);

                    // Show a toast message
                    Toast.makeText(context, "Copied: " + guest.getEmail(), Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        private void initializeSpinner(Spinner spinner, Guest guest) {
            // Create an ArrayAdapter with the required values
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.guest_status_array, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the Spinner
            spinner.setAdapter(adapter);

            // Set the selected response for the guest
            int position = adapter.getPosition(guest.getResponse());
            spinner.setSelection(position);

            // Add an onItemSelectedListener to update the guest response when the selection changes
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    guest.setResponse(parent.getItemAtPosition(pos).toString());
                    responseListener.onResponseChanged(); // Notify listener of response change
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        // Interface for response change listener
        public interface ResponseListener {
            void onResponseChanged();
        }
    }

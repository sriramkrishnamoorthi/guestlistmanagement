package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import com.example.myapplication.R;
import java.util.ArrayList;

public class GuestAdapter extends ArrayAdapter<Guest> {

    private Context context;
    private ArrayList<Guest> guestList;

    public GuestAdapter(Context context, ArrayList<Guest> guestList) {
        super(context, 0, guestList);
        this.context = context;
        this.guestList = guestList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.guest_list_item, parent, false);
        }

        // Get the guest at this position
        Guest guest = getItem(position);

        // Set the guest name and email in the EditText fields
        EditText guestNameEditText = convertView.findViewById(R.id.guest_name_edittext);
        EditText guestEmailEditText = convertView.findViewById(R.id.guest_email_edittext);
        guestNameEditText.setText(guest.getName());
        guestEmailEditText.setText(guest.getEmail());

        return convertView;
    }
}

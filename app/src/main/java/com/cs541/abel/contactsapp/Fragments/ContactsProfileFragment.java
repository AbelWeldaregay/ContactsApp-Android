package com.cs541.abel.contactsapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.cs541.abel.contactsapp.Activites.ImageActivity;
import com.cs541.abel.contactsapp.Adapters.PersonAdapter;
import com.cs541.abel.contactsapp.Models.Person;
import com.cs541.abel.contactsapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactsProfileFragment extends Fragment {

    private int selectedPosition;
    private TextView nameTextView;
    private TextView phoneNumberTextView;
    private ListView connectionsListView;
    private ImageView profilePictureImageView;
    private Uri filePath = Uri.parse("android.resource://com.cs541.abel.contactsapp/drawable/abelweldaregay");
    private ArrayList<com.cs541.abel.contactsapp.Models.Person> contacts;
    public static final String FILE_NAME = "ContactsObject";



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_contact_profile, container, false);
        // Set title bar

        this.contacts = new ArrayList<>();

        Bundle bundle = this.getArguments();
        this.selectedPosition = bundle.getInt("selectedPosition");
        loadData();

        this.nameTextView = view.findViewById(R.id.nameTextView);
        this.phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        this.connectionsListView = view.findViewById(R.id.connectionsListView);
        this.profilePictureImageView = view.findViewById(R.id.profilePictureImageView);
        this.filePath = Uri.parse(this.contacts.get(this.selectedPosition).getImagePath());
        this.profilePictureImageView.setImageURI(Uri.parse(this.contacts.get(this.selectedPosition).getImagePath()));
        this.nameTextView.setText(this.contacts.get(selectedPosition).getName());
        this.phoneNumberTextView.setText(this.contacts.get(selectedPosition).getPhoneNumber());

        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row,  this.contacts.get(this.selectedPosition).getConnections());
        this.connectionsListView.setAdapter(adapter);

        profilePictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateIntent(profilePictureImageView);
            }
        });

        connectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  Log.i("SELECTED POSITION: ", Integer.toString(selectedPosition));
                  Log.i("POSITION: ", Integer.toString(position));
                  Log.i("CONTACTS SIZE: ", Integer.toString(contacts.size()));
                  Log.i("CONNECTIONS SIZE", Integer.toString(contacts.get(selectedPosition).getConnections().size()));

                    filePath = Uri.parse(contacts.get(selectedPosition).getConnections().get(position).getImagePath());
                  profilePictureImageView.setImageURI(Uri.parse(contacts.get(selectedPosition).getConnections().get(position).getImagePath()));
                  nameTextView.setText(contacts.get(selectedPosition).getConnections().get(position).getName());
                  phoneNumberTextView.setText(contacts.get(selectedPosition).getConnections().get(position).getPhoneNumber());
                  PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, contacts.get(selectedPosition).getConnections().get(position).getConnections());
                  connectionsListView.setAdapter(adapter);

                  selectedPosition = contacts.indexOf(contacts.get(selectedPosition).getConnections().get(position));

            }
        });

        return view;


    }

    private void loadData() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("contacts", null);
        Type type = new TypeToken<ArrayList<Person>>() {}.getType();
        this.contacts = gson.fromJson(json, type);

        if(contacts == null) {
            this.contacts = new ArrayList<>();
        }

    }

    // MainActivity
    public void animateIntent(View view) {

        // Ordinary Intent for launching a new activity
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra("imagePath", filePath.toString());
        // Get the transition name from the string
        String transitionName = getString(R.string.transition_string);

        // Define the view that the animation will start from
        View viewStart = view;

        ActivityOptionsCompat options =

                ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                        viewStart,   // Starting view
                        transitionName    // The String
                );
        //Start the Intent
        ActivityCompat.startActivity(getContext(), intent, options.toBundle());

    }

    private void saveData() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(this.contacts);
        editor.putString("contacts", json);
        editor.apply();

    }



}

package com.cs541.abel.contactsapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ArrayList<com.cs541.abel.contactsapp.Models.Person> contacts;
    public static final String FILE_NAME = "ContactsObject";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_contact_profile, container, false);
        this.contacts = new ArrayList<com.cs541.abel.contactsapp.Models.Person>();

        Bundle bundle = this.getArguments();
        this.selectedPosition = getArguments().getInt("selectedPosition");
        loadData();
        Toast.makeText(getContext(), Integer.toString(selectedPosition), Toast.LENGTH_SHORT).show();
        this.nameTextView = view.findViewById(R.id.nameTextView);
        this.phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);
        this.connectionsListView = view.findViewById(R.id.connectionsListView);

        this.nameTextView.setText(this.contacts.get(selectedPosition).getName());
        this.phoneNumberTextView.setText(this.contacts.get(selectedPosition).getPhoneNumber());
        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row,  this.contacts.get(this.selectedPosition).getConnections());
        this.connectionsListView.setAdapter(adapter);

        connectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), Integer.toString(contacts.size()), Toast.LENGTH_SHORT).show();
                for(int i = 0; i < contacts.size(); i++) {

                    if(contacts.get(i).equals(contacts.get(selectedPosition).getConnections().get(position))){

                        nameTextView.setText(contacts.get(i).getName());
                        phoneNumberTextView.setText(contacts.get(i).getPhoneNumber());
                        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, contacts.get(selectedPosition).getConnections().get(position).getConnections());
                        connectionsListView.setAdapter(adapter);
                    }


                }


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
            this.contacts = new ArrayList<com.cs541.abel.contactsapp.Models.Person>();
        }

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

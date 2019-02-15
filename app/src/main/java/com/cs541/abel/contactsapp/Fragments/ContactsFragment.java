package com.cs541.abel.contactsapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cs541.abel.contactsapp.Adapters.PersonAdapter;
import com.cs541.abel.contactsapp.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import com.cs541.abel.contactsapp.Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 *
 */
public class ContactsFragment extends Fragment implements AdapterView.OnItemClickListener {


    private ListView contactsList;
    private Communicator communicator;
    private ArrayList<com.cs541.abel.contactsapp.Models.Person> contacts = new ArrayList<>();
    private static final String FILE_NAME = "ContactsObject";
    private Button addButton;
    private Button deleteButton;


    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        loadData();

        this.addButton = view.findViewById(R.id.addButton);
        this.contactsList = view.findViewById(R.id.contactsList);

        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, this.contacts);
        this.contactsList.setAdapter(adapter);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment contactDetailsFragment = new ContactDetailsFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, contactDetailsFragment).addToBackStack("")
                        .commit();
            }
        });

        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        communicator.respond(position);

    }


    public interface Communicator {

        public void respond(int index);

    }

    @Override
    public void onResume(){
        super.onResume();

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
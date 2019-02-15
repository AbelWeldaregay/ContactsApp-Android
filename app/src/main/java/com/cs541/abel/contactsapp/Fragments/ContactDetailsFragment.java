package com.cs541.abel.contactsapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.cs541.abel.contactsapp.Adapters.PersonAdapter;
import com.cs541.abel.contactsapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ContactDetailsFragment extends Fragment {

    ArrayList<com.cs541.abel.contactsapp.Models.Person> contacts;
    ArrayList<com.cs541.abel.contactsapp.Models.Person> connections = new ArrayList<com.cs541.abel.contactsapp.Models.Person>();
    public static final String FILE_NAME = "ContactsObject";
    private EditText nameEditText;
    private EditText phoneNumberEditText;
    private Button addPersonButton;
    private ListView connectionsListView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contactdetails, container, false);
        loadData();

        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, contacts);
        this.nameEditText = view.findViewById(R.id.personNameEditText);
        this.phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        this.addPersonButton = view.findViewById(R.id.addContactButton);

        this.connectionsListView = view.findViewById(R.id.connectionsListView);
        this.connectionsListView.setAdapter(adapter);

        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadData();
                final String name = nameEditText.getText().toString();
                final String phoneNumber = phoneNumberEditText.getText().toString();
                name.trim();
                phoneNumber.trim();

                if(name.isEmpty() || name == null) {
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(phoneNumber.isEmpty() || phoneNumber == null) {
                    Toast.makeText(getContext(), "Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
                } else {

                    addConnections(connectionsListView);
                    com.cs541.abel.contactsapp.Models.Person person = new com.cs541.abel.contactsapp.Models.Person(name, phoneNumber, connections);

                    boolean flag = false;
                    for(int i = 0; i< contacts.size(); i++){
                        if(person.equals(contacts.get(i))) {
                            flag = true;
                        }
                    }

                    if(flag == false) {
                        contacts.add(person);
                        saveData();
                        Toast.makeText(getContext(), "Successfully add to contacts", Toast.LENGTH_SHORT).show();
                        nameEditText.setText("");
                        phoneNumberEditText.setText("");
                        connections.clear();
                        uncheckAllChildCascade(connectionsListView);
                        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, contacts);
                        connectionsListView.setAdapter(adapter);

                    } else {

                        Toast.makeText(getContext(), person.getName() + " already exists in your contacts", Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });


        return view;
    }



    public void addConnections(ListView listView) {

        CheckBox cb;
        ListView mainListView = listView;

        for(int x = 0; x<mainListView.getChildCount(); x++) {
            cb = (CheckBox) mainListView.getChildAt(x).findViewById(R.id.checkItem);
            if(cb.isChecked()){
                int pos = (Integer) cb.getTag();
                this.connections.add(this.contacts.get(pos));
            }
        }

    }

    private void uncheckAllChildCascade(ViewGroup vg) {

        for(int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if(v instanceof CheckBox) {
                ( (CheckBox) v).setChecked(false);
            } else if(v instanceof ViewGroup) {
                uncheckAllChildCascade((ViewGroup) v);
            }
        }


    }

    private void loadData() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sharedPreferences.getString("contacts", null);
        Type type = new TypeToken<ArrayList<com.cs541.abel.contactsapp.Models.Person>>() {}.getType();
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

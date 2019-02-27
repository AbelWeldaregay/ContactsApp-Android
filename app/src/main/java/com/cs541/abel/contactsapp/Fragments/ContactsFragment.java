package com.cs541.abel.contactsapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import com.cs541.abel.contactsapp.Activites.ContactDetailsActivity;
import com.cs541.abel.contactsapp.Activites.ContactProfileActivity;
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
public class ContactsFragment extends Fragment  {


    private ListView contactsList;
    private ArrayList<com.cs541.abel.contactsapp.Models.Person> contacts = new ArrayList<>();
    private static final String FILE_NAME = "ContactsObject";
    private Button addButton;
    private Button deleteButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        loadData();

        this.addButton = view.findViewById(R.id.addButton);
        this.contactsList = view.findViewById(R.id.contactsList);
        this.deleteButton = view.findViewById(R.id.deleteButton);

        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, this.contacts);
        this.contactsList.setAdapter(adapter);


        this.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb;
                ListView mainListView = view.findViewById(R.id.contactsList);
                for (int x = 0; x<mainListView.getChildCount();x++){


                    cb = (CheckBox) mainListView.getChildAt(x).findViewById(R.id.checkItem);
                    if(cb.isChecked()){
                        int pos = (Integer) cb.getTag();

                        if(pos < contacts.size())
                        {
                            Log.i("positions","pos: " + Integer.toString(pos) + " size: " + contacts.size());
                            contacts.remove(pos);
                        }
                        else
                        {
                            pos = contacts.size() - 1;
                            Log.i("pos >= size: ","pos: " + Integer.toString(pos) + " size: " + contacts.size());

                            contacts.remove(pos);
                        }


                    }
                }
                saveData();
                PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, contacts);
                mainListView.setAdapter(adapter);

                Toast.makeText(getContext(), "Deleted Selected Items", Toast.LENGTH_LONG).show();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                    //Do some stuff
                    Fragment contactDetailsFragment = new ContactDetailsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment2_container, contactDetailsFragment)
                            .commit();

                } else {

                    Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
                    startActivity(intent);

                }




            }
        });

        this.contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundle = new Bundle();
                bundle.putInt("selectedPosition", position);


                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

                    Fragment contactsProfileFragment = new ContactsProfileFragment();
                    contactsProfileFragment.setArguments(bundle);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment2_container, contactsProfileFragment)
                            .commit();


                } else {

                    Intent intent = new Intent(getActivity(), ContactProfileActivity.class);
                    intent.putExtra("selectedPosition", position);

                    getActivity().startActivity(intent);

                }

            }
        });


        return view;
    }


    @Override
    public void onResume(){
        super.onResume();
        loadData();
        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, this.contacts);
        this.contactsList.setAdapter(adapter);

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

    private void saveData() {

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        String json = gson.toJson(this.contacts);
        editor.putString("contacts", json);
        editor.apply();

    }

}
package com.cs541.abel.contactsapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cs541.abel.contactsapp.Activites.ContactProfileActivity;
import com.cs541.abel.contactsapp.Activites.ImageActivity;
import com.cs541.abel.contactsapp.Adapters.PersonAdapter;
import com.cs541.abel.contactsapp.Models.Person;
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
    private Button pickImageButton;
    private ListView connectionsListView;
    private ImageView imageView;

    private Uri filePath = Uri.parse("android.resource://com.cs541.abel.contactsapp/drawable/abelweldaregay");
    private final int PICK_IMAGE_REQUEST = 71;
    Bundle sis;
    String nameInput;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){

            this.nameEditText.setText(savedInstanceState.getString("nameInput", "empty"));
            this.phoneNumberEditText.setText(savedInstanceState.getString("phoneNumberInput", "empty"));
            this.sis = savedInstanceState;
            this.nameInput = savedInstanceState.getString("nameInput", "empty");
            this.nameEditText.setText(this.nameInput);

            Log.i("onCreatedNameinput", "nameInput" + this.nameInput);

        }

    }

    public void chooseImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && data != null )
        {
               this.filePath = data.getData();
               this.imageView.setImageURI(this.filePath);
               Log.i("FILEPATH: ", filePath.toString());

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contactdetails, container, false);
        loadData();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        PersonAdapter adapter = new PersonAdapter(getContext(), R.layout.contacts_row, contacts);
        this.nameEditText = view.findViewById(R.id.personNameEditText);
        this.phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);
        this.imageView = view.findViewById(R.id.profilePictureImageView);
        this.imageView.setImageURI(filePath);
        this.pickImageButton = view.findViewById(R.id.pickImageButton);

        this.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateIntent(imageView);
            }
        });


        this.pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseImage();

            }
        });



        if(savedInstanceState != null) {

            Toast.makeText(getContext(), savedInstanceState.getString("nameInput", "empty"), Toast.LENGTH_SHORT).show();
            this.nameEditText.setText(savedInstanceState.getString("nameInput", "empty"));
            this.phoneNumberEditText.setText(savedInstanceState.getString("phoneNumberInput", "empty"));

        }


        this.addPersonButton = view.findViewById(R.id.addContactButton);

        this.connectionsListView = view.findViewById(R.id.connectionsListView);
        this.connectionsListView.setAdapter(adapter);

        this.connectionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContactProfileActivity.class);
                intent.putExtra("selectedPosition", position);
                startActivity(intent);
            }
        });

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
                    Person person = new Person(name, phoneNumber, connections, filePath.toString());

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
                        imageView.setImageURI(Uri.parse("android.resource://com.cs541.abel.contactsapp/drawable/abelweldaregay"));
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

    public void addPersonToConnections(Person person) {




        for(int i = 0; i < this.connections.size(); i++) {

            for(int j = 0; j < this.contacts.size(); j++) {

                if(this.contacts.get(j).equals(this.connections.get(i))) {

                    Log.i("contactsDetailsFragment", "they are equal!");
                    this.contacts.get(j).connections.add(person);

                }



            }

        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("nameInput", this.nameEditText.getText().toString());
        outState.putString("phoneNumberInput", this.phoneNumberEditText.getText().toString());

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState !=null){

            this.nameEditText.setText(savedInstanceState.getString("nameInput"));
            this.phoneNumberEditText.setText(savedInstanceState.getString("phoneNumberInput"));
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

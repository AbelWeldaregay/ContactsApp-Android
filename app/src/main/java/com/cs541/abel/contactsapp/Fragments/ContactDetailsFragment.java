package com.cs541.abel.contactsapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs541.abel.contactsapp.R;

import java.util.ArrayList;

public class ContactDetailsFragment extends Fragment {

    ArrayList<Person> contacts;
    ArrayList<Person> connections = new ArrayList<Person>();
    public static final String FILE_NAME = "ContactsObject";

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contactdetails, container, false);



        return view;
    }


}

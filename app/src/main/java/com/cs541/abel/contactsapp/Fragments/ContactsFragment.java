package com.cs541.abel.contactsapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.cs541.abel.contactsapp.R;
import java.util.ArrayList;
import com.cs541.abel.contactsapp.Models.*;


/**
 *
 */
public class ContactsFragment extends Fragment implements AdapterView.OnItemClickListener {


    ListView contactsList;
    Communicator communicator;
    ArrayList<Person> contacts = new ArrayList<Person>();
    public static final String FILE_NAME = "ContactsObject";


    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_contacts, container, false);

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

    public void checkBoxClicked(View view){
        // TODO
    }


}
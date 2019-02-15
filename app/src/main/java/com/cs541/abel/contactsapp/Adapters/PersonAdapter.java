package com.cs541.abel.contactsapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs541.abel.contactsapp.Models.Person;
import com.cs541.abel.contactsapp.R;

import java.util.ArrayList;


/**
 * Represents an adapter for the contacts listview
 * @author Abel Weldaregay
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    public static final String TAG = "PersonAdapter";
    private Context mContext;
    private int mResource;
    final ArrayList<Integer> checkedPositions = new ArrayList<Integer>();

    /**
     * Holds variables for a view
     * @author Abel Weldaregay
     */
    static class ViewHolder {

        TextView name;
        ImageView picture;
        CheckBox checkBox;

    }


    /**
     *
     * @param context
     * @param resource
     * @param objects
     */
    public PersonAdapter(Context context, int resource, ArrayList<Person> objects) {

        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;


    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String name = getItem(position).getName();
        String phoneNumber = getItem(position).getPhoneNumber();
        int id = getItem(position).getId();
        ArrayList<Person> connections = getItem(position).getConnections();

        Person person = new Person(name, phoneNumber, connections);
        ViewHolder holder;

        if(convertView == null){

            LayoutInflater inflater = LayoutInflater.from(this.mContext);
            convertView = inflater.inflate(this.mResource, parent, false);

            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.personNameTextView);
            holder.picture = convertView.findViewById(R.id.imageView);
            holder.checkBox = convertView.findViewById(R.id.checkItem);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        final Integer index = new Integer(position);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((CheckBox)(v)).isChecked()){

                    checkedPositions.add(index);
                } else if(checkedPositions.contains(index)) {
                    checkedPositions.remove(index);
                }

            }
        });

        holder.checkBox.setChecked(checkedPositions.contains(index));
        holder.checkBox.setTag(position);
        holder.name.setText(name);

        return convertView;

    }





}
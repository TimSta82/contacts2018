package com.example.tstaats.contacts2018;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    // Context is the class that will be using this adapter
    private Context context;
    private List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> list){
        super(context, R.layout.row_layout, list);
        this.context = context;
        this.contacts = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.row_layout, parent, false);

        TextView tvChar = convertView.findViewById(R.id.tv_char);
        TextView tvName = convertView.findViewById(R.id.tv_name);
        TextView tvEmail = convertView.findViewById(R.id.tv_email);

        tvChar.setText(contacts.get(position).getName().toUpperCase().charAt(0) + "");
        tvName.setText(contacts.get(position).getName());
        tvEmail.setText(contacts.get(position).getEmail());


        return convertView;

    }
}

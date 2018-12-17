package com.example.tstaats.contacts2018;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private MainActivity mainActivity;


    private Button btnContactList, btnNewContact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initMain(view);

        btnNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.fragmentSwitcher(new NewContactFragment(), "NewContactFragment", true);

            }
        });

        btnContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.fragmentSwitcher(new ContactListFragment(), "ContactListFragment", true);

            }
        });

        return view;
    }

    private void initMain(View view) {
        btnContactList = view.findViewById(R.id.btn_contactlist);
        btnNewContact = view.findViewById(R.id.btn_new_contact);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity)getActivity();
    }
}

package com.example.tstaats.contacts2018;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class ContactInfoFragment extends Fragment {

    private static final String TAG = "ContactInfoFragment";

    private MainActivity mainActivity;
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    private ImageView ivCall, ivEmail, ivEdit, ivDelete;
    private TextView tvChar, tvName;
    private Button btnSubmit;
    private EditText etName, etEmail, etNumber;
    private boolean edit = false;
    private int position;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);

        initInfo(view);

        showProgress(true);
        tvLoad.setText("Loading contact...please wait...");
        Bundle args = getArguments();

        if (args != null) {
            showProgress(false);
            position = args.getInt("position");
            Contact currentContact = ApplicationClass.contactList.get(position);
            Log.d(TAG, "onCreateView: currentContact: " + currentContact.toString());
            tvChar.setText(currentContact.getName().toUpperCase().charAt(0) + "");
            tvName.setText(currentContact.getName() + "");
            etName.setText(currentContact.getName());
            etEmail.setText(currentContact.getEmail());
            etNumber.setText(currentContact.getNumber());

        } else {
            showProgress(false);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String number = etNumber.getText().toString().trim();
                
                if (name.isEmpty() || email.isEmpty() || number.isEmpty()) {
                    Toast.makeText(mainActivity, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    ApplicationClass.contactList.get(position).setName(name);
                    ApplicationClass.contactList.get(position).setEmail(email);
                    ApplicationClass.contactList.get(position).setNumber(number);  
                    
                    showProgress(true);
                    tvLoad.setText("Updating contact...please wait...");
                    Backendless.Persistence.save(ApplicationClass.contactList.get(position), new AsyncCallback<Contact>() {
                        @Override
                        public void handleResponse(Contact response) {
                            tvChar.setText(ApplicationClass.contactList.get(position).getName().toUpperCase().charAt(0) + "");
                            tvName.setText(ApplicationClass.contactList.get(position).getName());
                            Toast.makeText(mainActivity, "Contact successfully updated", Toast.LENGTH_SHORT).show();
                            
                            showProgress(false);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(mainActivity, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgress(false);
                        }
                    });
                }


                //ApplicationClass.contactList.
            }
        });

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel: " + ApplicationClass.contactList.get(position).getNumber();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        ivEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ApplicationClass.contactList.get(position).getEmail();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, ApplicationClass.contactList.get(position).getEmail());
                startActivity(Intent.createChooser(intent, "Send mail to " +
                        ApplicationClass.contactList.get(position).getName()));
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edit = !edit;
                if (edit) {
                    etName.setVisibility(View.VISIBLE);
                    etEmail.setVisibility(View.VISIBLE);
                    etNumber.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                } else {
                    etName.setVisibility(View.GONE);
                    etEmail.setVisibility(View.GONE);
                    etNumber.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.GONE);

                }
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(mainActivity);
                dialog.setMessage("Are you sure you want to delete the contact?");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showProgress(true);
                        tvLoad.setText("Deleting contact...please wait...");

                        Backendless.Persistence.of(Contact.class).remove(ApplicationClass.contactList.get(position), new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                ApplicationClass.contactList.remove(position);
                                Toast.makeText(mainActivity, "Contact successfully removed", Toast.LENGTH_SHORT).show();
                                mainActivity.fragmentSwitcher(new MainFragment(), "ContactListFragment", false);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(mainActivity, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        });
                    }
                });
                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();

            }
        });


        return view;
    }


    private void initInfo(View view) {
        mProgressView = view.findViewById(R.id.login_progress);
        mLoginFormView = view.findViewById(R.id.login_form);
        tvLoad = view.findViewById(R.id.tvLoad);

        ivCall = view.findViewById(R.id.iv_call);
        ivEmail = view.findViewById(R.id.iv_email);
        ivEdit = view.findViewById(R.id.iv_edit);
        ivDelete = view.findViewById(R.id.iv_delete);

        tvChar = view.findViewById(R.id.tv_char);
        tvName = view.findViewById(R.id.tv_name);

        btnSubmit = view.findViewById(R.id.btn_submit);
        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etNumber = view.findViewById(R.id.et_number);

        etName.setVisibility(View.GONE);
        etEmail.setVisibility(View.GONE);
        etNumber.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

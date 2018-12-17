package com.example.tstaats.contacts2018;


import android.app.Application;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.util.List;

// very first class that the application is running, even before the MainActivity
public class ApplicationClass extends Application {

    public static final String APPLICATION_ID = "E3EA98F1-9108-6F1C-FFA4-3906A506F500";
    public static final String API_KEY = "599B642B-C60C-0B19-FF50-28FACBF7A000";
    public static final String SERVER_URL = "https://api.backendless.com";

    public static BackendlessUser user;
    public static List<Contact> contactList;

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl(SERVER_URL);
        Backendless.initApp(getApplicationContext(),
                APPLICATION_ID,
                API_KEY);

    }
}

package com.example.tstaats.contacts2018;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = new LoginFragment();

        fragmentSwitcher(fragment, "LoginFragment", true);

        Calendar calendar = new GregorianCalendar();

/*        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Log.d(TAG, "onCreate: dayOfWeek: " + dayOfWeek + " dayOfMonth: " + dayOfMonth + " month: " + month + "\n---------------------------------");
        calendar.set(1950, 10, 5,1,1,1);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);*/

        int month = 0;
        for (int i = 0; i < 100; i++) {
            calendar.set(1950, 11, i,1,1,1);
            if (month != calendar.get(Calendar.MONTH)){
                Log.d(TAG, "onCreate: monthswitch #########################################");
            }
            month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int year = calendar.get(Calendar.YEAR);
            Log.d(TAG, "onCreate: dayOfMonth: " + dayOfMonth + " month: " + month + " year: " + year + "\n---------------------------------");

        }


    }

    public void fragmentSwitcher(Fragment fragment, String name, boolean toBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if(toBackStack){
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

}

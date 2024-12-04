package edu.uga.cs.roommateshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

/**
 * Main activity for the Roommate Shopping app.
 */
public class MainActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "MainActivity";

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState for getting the saved Bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(DEBUG_TAG, "RoommateShopping: MainActivity.onCreate()");

        Button signInButton = findViewById(R.id.button1);
        Button registerButton = findViewById(R.id.button2);

        signInButton.setOnClickListener(new SignInButtonClickListener());
        registerButton.setOnClickListener(new RegisterButtonClickListener());
    }

    /**
     * OnClickListener for the register button. Starts the registration activity when clicked.
     */
    private class RegisterButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    /**
     * OnClickListener for the sign-in button. Starts the sign-in activity when clicked.
     */
    private class SignInButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), SignInActivity.class);
            view.getContext().startActivity(intent);
        }
    }


    /**
     * Called when the activity is starting. Logs the event and calls the superclass's onStart method.
     */
    @Override
    protected void onStart() {
        Log.d(DEBUG_TAG, "RoommateShopping: MainActivity.onStart()");
        super.onStart();
    }

    /**
     * Called when the activity will start interacting with the user. Logs the event and calls the superclass's onResume method.
     */
    @Override
    protected void onResume() {
        Log.d(DEBUG_TAG, "RoommateShopping: MainActivity.onResume()");
        super.onResume();
    }

    /**
     * Called when the system is about to start resuming another activity. Logs the event and calls the superclass's onPause method.
     */
    @Override
    protected void onPause() {
        Log.d(DEBUG_TAG, "RoommateShopping: MainActivity.onPause()");
        super.onPause();
    }

    /**
     * Called when the activity is no longer visible to the user. Logs the event and calls the superclass's onStop method.
     */
    @Override
    protected void onStop() {
        Log.d(DEBUG_TAG, "RoommateShopping: MainActivity.onStop()");
        super.onStop();
    }

    /**
     * Called when the activity is being destroyed. Logs the event and calls the superclass's onDestroy method.
     */
    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "RoommateShopping: MainActivity.onDestroy()");
        super.onDestroy();
    }

    /**
     * Called after the activity has been stopped, just prior to it being started again. Logs the event and calls the superclass's onRestart method.
     */
    @Override
    protected void onRestart() {
        Log.d(DEBUG_TAG, "RoommateShopping: MainActivity.onRestart()");
        super.onRestart();
    }
}


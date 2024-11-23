package edu.uga.cs.roommateshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RoommateManagementActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "ManagementActivity";

    private TextView signedInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roommate_management);

        Log.d(DEBUG_TAG, "RoommateManagementActivity.onCreate()");

        Button newLeadButton = findViewById(R.id.button1);
        Button reviewLeadsButton = findViewById(R.id.button2);
        Button markAsPurchasedButton = findViewById(R.id.button3);  // Find button3
        signedInTextView = findViewById(R.id.textView3);
        Button logOutButton = findViewById(R.id.button7);

        newLeadButton.setOnClickListener(new NewLeadButtonClickListener());
        reviewLeadsButton.setOnClickListener(new ReviewLeadsButtonClickListener());
        markAsPurchasedButton.setOnClickListener(new MarkAsPurchasedButtonClickListener()); // Set listener for button3
        logOutButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoommateManagementActivity.this, MainActivity.class);
            startActivity(intent);
        });

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    String userEmail = currentUser.getEmail();
                    signedInTextView.setText("Signed in as: " + userEmail);
                } else {
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_out");
                    signedInTextView.setText("Signed in as: not signed in");
                }
            }
        });
    }

    private class NewLeadButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewItemActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class ReviewLeadsButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    private class MarkAsPurchasedButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MarkAsPurchasedActivity.class);  // Start MarkAsPurchasedActivity
            view.getContext().startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onRestart()");
        super.onRestart();
    }
}

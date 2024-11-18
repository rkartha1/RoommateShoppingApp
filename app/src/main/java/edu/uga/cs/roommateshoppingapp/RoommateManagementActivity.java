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

        Log.d( DEBUG_TAG, "JobLeadManagementActivity.onCreate()" );

        Button newLeadButton = findViewById(R.id.button1);
        Button reviewLeadsButton = findViewById(R.id.button2);
        signedInTextView = findViewById( R.id.textView3 );

        //newLeadButton.setOnClickListener( new NewLeadButtonClickListener() );
        //reviewLeadsButton.setOnClickListener( new ReviewLeadsButtonClickListener() );

        // Setup a listener for a change in the sign in status (authentication status change)
        // when it is invoked, check if a user is signed in and update the UI text view string,
        // as needed.
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if( currentUser != null ) {
                    // User is signed in
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    String userEmail = currentUser.getEmail();
                    signedInTextView.setText( "Signed in as: " + userEmail );
                } else {
                    // User is signed out
                    Log.d( DEBUG_TAG, "onAuthStateChanged:signed_out" );
                    signedInTextView.setText( "Signed in as: not signed in" );
                }
            }
        });
    }

   /* private class NewLeadButtonClickListener implements View.OnClickListener {
       @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewJobLeadActivity.class);
            view.getContext().startActivity( intent );
        }
    }

    private class ReviewLeadsButtonClickListener implements View.OnClickListener {
       @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewJobLeadsActivity.class);
            view.getContext().startActivity(intent);
        }
    } */

    // These activity callback methods are not needed and are for edational purposes only
    @Override
    protected void onStart() {
        Log.d( DEBUG_TAG, "RoommateShopping: ManagementActivity.onStart()" );
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "RoommateShopping: ManagementActivity.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "RoommateShopping: ManagementActivity.onPause()" );
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d( DEBUG_TAG, "RoommateShopping: ManagementActivity.onStop()" );
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d( DEBUG_TAG, "RoommateShopping: ManagementActivity.onDestroy()" );
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d( DEBUG_TAG, "RoommateShopping: ManagementActivity.onRestart()" );
        super.onRestart();
    }
}
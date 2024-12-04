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

/**
 * Activity for managing various roommate shopping tasks.
 */
public class RoommateManagementActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "ManagementActivity";

    private TextView signedInTextView;

    /**
     * Initializes the RoommateManagementActivity by setting up the UI elements, including buttons
     * for managing shopping tasks and the logout functionality.
     *
     * @param savedInstanceState The saved instance state, if any, to restore the previous state of the activity.
     */
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
        Button checkout = findViewById(R.id.button5);
        Button viewPurchases = findViewById(R.id.button6);
        Button settleCosts = findViewById(R.id.button8);

        newLeadButton.setOnClickListener(new NewLeadButtonClickListener());
        reviewLeadsButton.setOnClickListener(new ReviewLeadsButtonClickListener());
        markAsPurchasedButton.setOnClickListener(new MarkAsPurchasedButtonClickListener()); // Set listener for button3
        logOutButton.setOnClickListener(v -> {
            Intent intent = new Intent(RoommateManagementActivity.this, MainActivity.class);
            startActivity(intent);
        });

        checkout.setOnClickListener(v -> {
            Intent intent = new Intent(RoommateManagementActivity.this, ViewBasketActivity.class);
            startActivity(intent);
        });

        settleCosts.setOnClickListener(v -> {
            Intent intent = new Intent(RoommateManagementActivity.this, SettleCostActivity.class);
            startActivity(intent);
        });

        viewPurchases.setOnClickListener(new PurchasedItemButtonClickListener());

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

    /**
     * Listener for the "New Lead" button. Starts the NewItemActivity to add a new shopping item.
     */
    private class NewLeadButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewItemActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Listener for the "Purchased Items" button. Starts the PurchasedItemListActivity to view the purchased items.
     */
    private class PurchasedItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), PurchasedItemListActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Listener for the "Review Leads" button. Starts the ShoppingListActivity to review shopping leads.
     */
    private class ReviewLeadsButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ShoppingListActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Listener for the "Mark as Purchased" button. Starts the MarkAsPurchasedActivity to mark items as purchased.
     */
    private class MarkAsPurchasedButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), MarkAsPurchasedActivity.class);  // Start MarkAsPurchasedActivity
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Called when the activity is starting. Logs the activity's start event.
     */
    @Override
    protected void onStart() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onStart()");
        super.onStart();
    }

    /**
     * Called when the activity has become visible to the user. Logs the activity's resume event.
     */
    @Override
    protected void onResume() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onResume()");
        super.onResume();
    }

    /**
     * Called when the system is about to start resuming a previous activity. Logs the activity's pause event.
     */
    @Override
    protected void onPause() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onPause()");
        super.onPause();
    }

    /**
     * Called when the activity is no longer visible to the user. Logs the activity's stop event.
     */
    @Override
    protected void onStop() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onStop()");
        super.onStop();
    }

    /**
     * Called when the activity is being destroyed. Logs the activity's destroy event.
     */
    @Override
    protected void onDestroy() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onDestroy()");
        super.onDestroy();
    }

    /**
     * Called when the activity is being restarted. Logs the activity's restart event.
     */
    @Override
    protected void onRestart() {
        Log.d(DEBUG_TAG, "RoommateShopping: ManagementActivity.onRestart()");
        super.onRestart();
    }
}


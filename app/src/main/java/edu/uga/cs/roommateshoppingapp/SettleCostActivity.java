package edu.uga.cs.roommateshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Activity that manages the cost settlement among roommates.
 */
public class SettleCostActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView totalSpentTextView;
    private TextView averageSpentTextView;
    private TextView roommateDetailsTextView;

    private Button logOut;
    private Button goBack;

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState the state of the activity if it was previously paused.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle_cost);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("purchasedItems");

        // Initialize UI components
        totalSpentTextView = findViewById(R.id.totalSpentTextView);
        averageSpentTextView = findViewById(R.id.averageSpentTextView);
        roommateDetailsTextView = findViewById(R.id.roommateDetailsTextView);
        logOut = findViewById(R.id.button9);
        goBack = findViewById(R.id.button10);

        logOut.setOnClickListener(v -> {
            Intent intent = new Intent(SettleCostActivity.this, MainActivity.class);
            startActivity(intent);
        });

        goBack.setOnClickListener(v -> {
            Intent intent = new Intent(SettleCostActivity.this, RoommateManagementActivity.class);
            startActivity(intent);
        });

        // Fetch data and calculate costs
        fetchAndCalculateCosts();
    }

    /**
     * Fetches the purchase data from Firebase and calculates the costs.
     */
    private void fetchAndCalculateCosts() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<PurchasedItemGroup> purchasedItemGroups = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PurchasedItemGroup group = snapshot.getValue(PurchasedItemGroup.class);
                    if (group != null) {
                        purchasedItemGroups.add(group);
                    }
                }

                calculateAndDisplayResults(purchasedItemGroups);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FirebaseError", "Database error: " + databaseError.getMessage());
                Toast.makeText(SettleCostActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Calculates the total and average spending of roommates and displays the results.
     *
     * @param purchasedItemGroups a list of all purchased item groups.
     */
    private void calculateAndDisplayResults(List<PurchasedItemGroup> purchasedItemGroups) {
        Map<String, Double> roommateSpending = new HashMap<>();
        double totalSpent = 0;

        // Aggregate spending
        for (PurchasedItemGroup group : purchasedItemGroups) {
            totalSpent += group.getTotalPrice();
            roommateSpending.put(group.getRoommateEmail(),
                    roommateSpending.getOrDefault(group.getRoommateEmail(), 0.0) + group.getTotalPrice());
        }

        double averageSpent = totalSpent / roommateSpending.size();

        StringBuilder detailsBuilder = new StringBuilder();
        for (Map.Entry<String, Double> entry : roommateSpending.entrySet()) {
            double difference = entry.getValue() - averageSpent;
            detailsBuilder.append("Roommate: ").append(entry.getKey())
                    .append("\nSpent: $").append(entry.getValue())
                    .append("\nDifference: $").append(difference)
                    .append("\n\n");
        }

        totalSpentTextView.setText("Total Spent: $" + totalSpent);
        averageSpentTextView.setText("Average Spent: $" + averageSpent);
        roommateDetailsTextView.setText(detailsBuilder.toString());

        clearAllPurchases();
    }

    /**
     * Clears all purchase data from Firebase.
     */
    private void clearAllPurchases() {
        databaseReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "All purchases cleared successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("FirebaseError", "Error clearing purchases: " + task.getException());
            }
        });
    }
}


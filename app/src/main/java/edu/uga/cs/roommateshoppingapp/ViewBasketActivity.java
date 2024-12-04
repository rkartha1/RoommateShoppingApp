package edu.uga.cs.roommateshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Activity that displays a shopping basket where the user can view and manage purchased items.
 */
public class ViewBasketActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "ViewBasketActivity"; // Debugging tag for logging
    private ListView recentlyPurchasedListView; // ListView for displaying purchased items
    private ArrayAdapter<String> adapter; // Adapter for the ListView
    private ArrayList<String> recentlyPurchasedList; // List of recently purchased items
    private DatabaseReference recentlyPurchasedRef; // Firebase reference for recently purchased items
    private DatabaseReference shoppingListRef; // Firebase reference for shopping list
    private DatabaseReference purchasedItemsRef; // Firebase reference for purchased items
    private EditText totalPriceEditText; // EditText for the total price
    private Button submitButton; // Button to submit the purchase

    /**
     * Initializes the activity, sets up the UI components, and loads the recently purchased items.
     *
     * @param savedInstanceState Bundle containing saved instance state (if any).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_basket);

        // Initialize UI components
        recentlyPurchasedListView = findViewById(R.id.recently_purchased_list_view);
        totalPriceEditText = findViewById(R.id.total_price_edit_text);
        submitButton = findViewById(R.id.submit_button);

        recentlyPurchasedList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recentlyPurchasedList);
        recentlyPurchasedListView.setAdapter(adapter);

        // Set up item click listener to handle item deletion
        recentlyPurchasedListView.setOnItemClickListener((parent, view, position, id) -> {
            String itemString = recentlyPurchasedList.get(position);
            String[] parts = itemString.split(" - ");
            if (parts.length == 2) {
                String itemName = parts[0];
                String itemQuantity = parts[1];

                // Find the item ID from Firebase and show the delete dialog
                recentlyPurchasedRef.orderByChild("itemName").equalTo(itemName).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ShoppingItem item = snapshot.getValue(ShoppingItem.class);
                            if (item != null) {
                                item.setItemId(snapshot.getKey());  // Set the item ID
                                showDeleteDialog(item);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(DEBUG_TAG, "Error finding item: " + databaseError.getMessage());
                    }
                });
            }
        });

        // Initialize Firebase references
        recentlyPurchasedRef = FirebaseDatabase.getInstance().getReference("recentlyPurchased");
        shoppingListRef = FirebaseDatabase.getInstance().getReference("shoppingList");
        purchasedItemsRef = FirebaseDatabase.getInstance().getReference("purchasedItems");

        // Load the recently purchased items into the list
        recentlyPurchasedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recentlyPurchasedList.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    ShoppingItem item = itemSnapshot.getValue(ShoppingItem.class);
                    if (item != null) {
                        recentlyPurchasedList.add(item.getItemName() + " - " + item.getItemQuantity());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(DEBUG_TAG, "Error reading recently purchased list: " + databaseError.getMessage());
            }
        });

        // Set up the submit button for checking out
        submitButton.setOnClickListener(v -> {
            String totalPriceStr = totalPriceEditText.getText().toString().trim();
            if (!totalPriceStr.isEmpty()) {
                double totalPrice = Double.parseDouble(totalPriceStr);
                submitPurchase(totalPrice);
            } else {
                Toast.makeText(ViewBasketActivity.this, "Please enter the total price", Toast.LENGTH_SHORT).show();
            }
        });

        // Go Back Button
        Button goBackButton = findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(ViewBasketActivity.this, RoommateManagementActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Displays a dialog confirming whether the user wants to delete an item from the basket.
     *
     * @param item The ShoppingItem object to be deleted.
     */
    private void showDeleteDialog(ShoppingItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Item");

        builder.setMessage("Are you sure you want to remove " + item.getItemName() + " from your basket? This will move it back to the shopping list.");

        builder.setPositiveButton("Delete", (dialog, which) -> {
            moveItemBackToShoppingList(item);
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Moves an item from the recently purchased list back to the shopping list in Firebase.
     *
     * @param item The ShoppingItem to be moved back.
     */
    private void moveItemBackToShoppingList(ShoppingItem item) {
        shoppingListRef.push().setValue(item);

        // Remove the item from the recently purchased list
        recentlyPurchasedRef.child(item.getItemId()).removeValue()
                .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Item moved back to shopping list and deleted from recently purchased"))
                .addOnFailureListener(e -> Log.d(DEBUG_TAG, "Error moving item back to shopping list: " + e.getMessage()));

        // Refresh the recently purchased list
        recentlyPurchasedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recentlyPurchasedList.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    ShoppingItem item = itemSnapshot.getValue(ShoppingItem.class);
                    if (item != null) {
                        recentlyPurchasedList.add(item.getItemName() + " - " + item.getItemQuantity());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(DEBUG_TAG, "Error refreshing recently purchased list: " + databaseError.getMessage());
            }
        });
    }

    /**
     * Submits the purchase and records it in Firebase.
     *
     * @param totalPrice The total price of the purchase.
     */
    private void submitPurchase(double totalPrice) {
        // Get the current signed-in user's email
        String roommateEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        // Record the purchase and total price with the roommate's email
        recentlyPurchasedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Create a list to store the purchased items
                ArrayList<ShoppingItem> purchasedItems = new ArrayList<>();

                // Add each item to the purchased items list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ShoppingItem item = snapshot.getValue(ShoppingItem.class);
                    if (item != null) {
                        purchasedItems.add(item);
                    }
                }

                // Create a new purchase record
                PurchaseRecord purchaseRecord = new PurchaseRecord(roommateEmail, purchasedItems, totalPrice);

                // Store the purchase record in the database
                purchasedItemsRef.push().setValue(purchaseRecord)
                        .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Purchase recorded"))
                        .addOnFailureListener(e -> Log.d(DEBUG_TAG, "Error recording purchase: " + e.getMessage()));

                // Clear the shopping basket
                recentlyPurchasedRef.removeValue()
                        .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Basket cleared"))
                        .addOnFailureListener(e -> Log.d(DEBUG_TAG, "Error clearing the basket: " + e.getMessage()));

                // Clear the total price EditText
                totalPriceEditText.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(DEBUG_TAG, "Error submitting purchase: " + databaseError.getMessage());
            }
        });
    }
}




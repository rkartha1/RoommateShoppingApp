package edu.uga.cs.roommateshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Activity that displays a list of purchased items for roommates.
 */
public class PurchasedItemListActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "PurchasedItemListActivity";
    private ListView purchasedItemListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> purchasedItemList;
    private DatabaseReference purchasedItemListRef;
    private DatabaseReference shoppingListRef;

    /**
     * Initializes the activity, sets up UI components, and retrieves data from Firebase.
     *
     * @param savedInstanceState a Bundle containing the activity's previously saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_item_list);

        purchasedItemListView = findViewById(R.id.purchased_item_list_view);
        purchasedItemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, purchasedItemList);
        purchasedItemListView.setAdapter(adapter);

        purchasedItemListView.setOnItemClickListener((parent, view, position, id) -> {
            String itemString = purchasedItemList.get(position);
            String[] parts = itemString.split(" - ");

            if (parts.length < 3) {
                Log.d(DEBUG_TAG, "Invalid item string format: " + itemString);
                return;
            }

            String roommateEmail = parts[0];  // Extract roommateEmail
            String itemName = parts[1];       // Extract itemName
            String totalPrice = parts[2];     // Extract totalPrice

            // Firebase query to find items based on roommateEmail
            purchasedItemListRef.orderByChild("roommateEmail").equalTo(roommateEmail)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.exists()) {
                                Log.d(DEBUG_TAG, "No data found for roommateEmail: " + roommateEmail);
                                return;
                            }
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                PurchasedItemGroup items = snapshot.getValue(PurchasedItemGroup.class);
                                if (items != null) {
                                    items.setGroupID(snapshot.getKey());
                                    showEditDeleteDialog(items);
                                    // Log the items to see if they are correctly retrieved
                                    Log.d(DEBUG_TAG, "Found item group: " + items);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(DEBUG_TAG, "Error finding item: " + databaseError.getMessage());
                        }
                    });
        });

        Button goBackButton = findViewById(R.id.go_back_button1);
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(PurchasedItemListActivity.this, RoommateManagementActivity.class);
            startActivity(intent);
        });


        purchasedItemListRef = FirebaseDatabase.getInstance().getReference("purchasedItems");
        shoppingListRef = FirebaseDatabase.getInstance().getReference("shoppingList");

        purchasedItemListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                purchasedItemList.clear();  // Clear the list before adding new data

                if (!dataSnapshot.exists()) {
                    Log.d(DEBUG_TAG, "No data available in the database");
                    return;
                }

                // Iterate over each group in purchasedItems (like "group1")
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    Calendar calendar = Calendar.getInstance();

                    // Create a SimpleDateFormat to format the date as "yyyy-MM-dd"
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    // Format the current date and store it as a string
                    String currentDate = sdf.format(calendar.getTime());
                    PurchasedItemGroup group = groupSnapshot.getValue(PurchasedItemGroup.class);


                    if (group != null && group.getPurchasedItems() != null) {
                        String roommateEmail = group.getRoommateEmail();
                        double totalPrice = group.getTotalPrice();

                        // Log the group to see if it's correctly retrieved
                        Log.d(DEBUG_TAG, "Group: " + roommateEmail + " - Total Price: " + totalPrice);
                        String itemDisplay = roommateEmail + " - " + currentDate + " - ";
                        // Iterate over the purchased items for each group
                        for (PurchasedItem item : group.getPurchasedItems()) {
                            // Create a formatted string: roommateEmail - itemName - totalPrice
                            itemDisplay += item.getItemName() + " - ";
                        }
                        itemDisplay += totalPrice;
                        purchasedItemList.add(itemDisplay);
                    }

                }

                // Notify the adapter to update the ListView
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(DEBUG_TAG, "Error reading purchased items: " + databaseError.getMessage());
            }
        });
    }

    /**
     * Adds a new item to the shopping list.
     *
     * @param name     the name of the item.
     * @param quantity the quantity of the item.
     */
    public void addNewItem(String name, String quantity) {
        DatabaseReference newItemRef = shoppingListRef.push();
        String itemId = newItemRef.getKey();
        ShoppingItem newItem = new ShoppingItem(itemId, name, quantity);
        newItemRef.setValue(newItem);
    }


    /**
     * Displays a dialog for editing or deleting a purchased item group.
     *
     * @param items the purchased item group to edit or delete.
     */
    private void showEditDeleteDialog(PurchasedItemGroup items) {
        if (items.getPurchasedItems() == null) {
            Log.d(DEBUG_TAG, "Purchased items list is null. Initializing to an empty list.");
            items.setPurchasedItems(new ArrayList<>());
        }

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Item");

        // Inflate the custom dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_delete_purchase, null);
        builder.setView(dialogView);

        // Get references to the dialog components
        EditText totalPriceEditText = dialogView.findViewById(R.id.edit_total_price);
        ListView itemList = dialogView.findViewById(R.id.purchased_items_list);

        // Set the total price value
        totalPriceEditText.setText(String.valueOf(items.getTotalPrice()));

        // Set up the ListView with an ArrayAdapter
        ArrayAdapter<PurchasedItem> itemAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items.getPurchasedItems()
        );
        itemList.setAdapter(itemAdapter);

        // Handle ListView item click for deletion
        itemList.setOnItemClickListener((parent, view, position, id) -> {
            PurchasedItem selectedItem = items.getPurchasedItems().get(position);

            // Ask for confirmation before deleting the item
            new AlertDialog.Builder(this)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete " + selectedItem.getItemName() + "?")
                    .setPositiveButton("Yes", (dialog1, which) -> {
                        addNewItem(selectedItem.getItemName(), selectedItem.getItemQuantity());
                        items.getPurchasedItems().remove(position);
                        itemAdapter.notifyDataSetChanged();

                        // Update Firebase
                        if (items.getGroupID() != null) {
                            DatabaseReference itemGroupRef = purchasedItemListRef.child(items.getGroupID());
                            itemGroupRef.setValue(items)
                                    .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Item deleted successfully"))
                                    .addOnFailureListener(e -> Log.e(DEBUG_TAG, "Failed to delete item", e));
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // Handle dialog buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            try {
                double totalPrice = Double.parseDouble(totalPriceEditText.getText().toString());
                items.setTotalPrice(totalPrice);

                // Update Firebase
                if (items.getGroupID() != null) {
                    DatabaseReference itemGroupRef = purchasedItemListRef.child(items.getGroupID());
                    itemGroupRef.setValue(items)
                            .addOnSuccessListener(aVoid -> {
                                Log.d(DEBUG_TAG, "Total price updated successfully");
                                Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Log.e(DEBUG_TAG, "Failed to update total price", e);
                                Toast.makeText(this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                            });
                }
            } catch (NumberFormatException e) {
                Log.e(DEBUG_TAG, "Invalid number format: " + totalPriceEditText.getText().toString(), e);
                Toast.makeText(this, "Invalid price entered", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.setNeutralButton("Delete Group", (dialog, which) -> {
            if (items.getGroupID() != null) {
                purchasedItemListRef.child(items.getGroupID()).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Log.d(DEBUG_TAG, "Group deleted successfully");
                            Toast.makeText(this, "Group deleted", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(DEBUG_TAG, "Failed to delete group", e);
                            Toast.makeText(this, "Failed to delete group", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        builder.create().show();
    }


}


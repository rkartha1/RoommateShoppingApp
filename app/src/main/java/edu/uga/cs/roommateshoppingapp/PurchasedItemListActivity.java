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


public class PurchasedItemListActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "PurchasedItemListActivity";
    private ListView purchasedItemListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> purchasedItemList;
    private DatabaseReference purchasedItemListRef;
    private DatabaseReference shoppingListRef;

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

        Button logOutButton = findViewById(R.id.log_out_button2);
        logOutButton.setOnClickListener(v -> {
            Intent intent = new Intent(PurchasedItemListActivity.this, MainActivity.class);
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

    public void addNewItem(String name, String quantity) {
        DatabaseReference newItemRef = shoppingListRef.push();
        String itemId = newItemRef.getKey();
        ShoppingItem newItem = new ShoppingItem(itemId, name, quantity);
        newItemRef.setValue(newItem);
    }

    private void showEditDeleteDialog(PurchasedItemGroup items) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Item");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_delete_purchase, null);
        builder.setView(dialogView);

        EditText totalPriceEditText = dialogView.findViewById(R.id.edit_total_price);
        ListView itemList = dialogView.findViewById(R.id.purchased_items_list);

        // Set the current total price in the EditText
        totalPriceEditText.setText(String.valueOf(items.getTotalPrice()));

        // Populate ListView with purchased items
        ArrayAdapter<PurchasedItem> itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items.getPurchasedItems());
        itemList.setAdapter(itemAdapter);

        // Handle item deletion
        itemList.setOnItemClickListener((parent, view, position, id) -> {
            PurchasedItem selectedItem = items.getPurchasedItems().get(position);

            // Confirm deletion of the item
            new AlertDialog.Builder(this)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete " + selectedItem.getItemName() + "?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        // Remove the item from the group's purchasedItems list
                        items.getPurchasedItems().remove(selectedItem);

                        // Update Firebase for the modified group
                        purchasedItemListRef.child(items.getGroupID()).setValue(items)
                                .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Item removed successfully"))
                                .addOnFailureListener(e -> Log.d(DEBUG_TAG, "Error removing item: " + e.getMessage()));

                        // Add the deleted item back to the shopping list
                        addNewItem(selectedItem.getItemName(), selectedItem.getItemQuantity());

                        // Refresh the ListView
                        itemAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Handle price update
        builder.setPositiveButton("Update", (dialog, which) -> {
            String updatedTotalPrice = totalPriceEditText.getText().toString().trim();

            if (!updatedTotalPrice.isEmpty()) {
                items.setTotalPrice(Double.parseDouble(updatedTotalPrice));

                // Update Firebase with the new total price
                if (items.getGroupID() != null) {
                    purchasedItemListRef.child(items.getGroupID()).setValue(items)
                            .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Total price updated successfully"))
                            .addOnFailureListener(e -> Log.d(DEBUG_TAG, "Error updating total price: " + e.getMessage()));
                } else {
                    Log.d(DEBUG_TAG, "Error: groupID is null");
                }
            }
        });

        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


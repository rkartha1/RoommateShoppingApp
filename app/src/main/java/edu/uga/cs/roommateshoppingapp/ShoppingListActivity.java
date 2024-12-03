package edu.uga.cs.roommateshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "ShoppingListActivity";
    private ListView shoppingListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> shoppingList;
    private DatabaseReference shoppingListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        shoppingListView = findViewById(R.id.shopping_list_view);
        shoppingList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shoppingList);
        shoppingListView.setAdapter(adapter);

        shoppingListView.setOnItemClickListener((parent, view, position, id) -> {
            String itemString = shoppingList.get(position);
            String[] parts = itemString.split(" - ");
                    if (parts.length == 2) {
                        String itemName = parts[0];
                        String itemQuantity = parts[1];

                        // Find the item ID from the Firebase database
                        shoppingListRef.orderByChild("itemName").equalTo(itemName).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    ShoppingItem item = snapshot.getValue(ShoppingItem.class);
                                    if (item != null) {
                                        item.setItemId(snapshot.getKey());  // Set the item ID
                                        showEditDeleteDialog(item);
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


        Button goBackButton = findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShoppingListActivity.this, RoommateManagementActivity.class);
            startActivity(intent);
        });


        shoppingListRef = FirebaseDatabase.getInstance().getReference("shoppingList");

        shoppingListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shoppingList.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    ShoppingItem item = itemSnapshot.getValue(ShoppingItem.class);
                    if (item != null) {
                        shoppingList.add(item.getItemName() + " - " + item.getItemQuantity());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(DEBUG_TAG, "Error reading shopping list: " + databaseError.getMessage());
            }
        });
    }

    // Method to add a new item
    public void addNewItem(String name, String quantity) {
        DatabaseReference newItemRef = shoppingListRef.push();
        String itemId = newItemRef.getKey();
        ShoppingItem newItem = new ShoppingItem(itemId, name, quantity);
        newItemRef.setValue(newItem);
    }

    private void showEditDeleteDialog(ShoppingItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit or Delete Item");

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_delete_item, null);
        builder.setView(dialogView);

        EditText itemNameEditText = dialogView.findViewById(R.id.edit_item_name);
        EditText itemQuantityEditText = dialogView.findViewById(R.id.edit_item_quantity);

        itemNameEditText.setText(item.getItemName());
        itemQuantityEditText.setText(item.getItemQuantity());

        builder.setPositiveButton("Update", (dialog, which) -> {
            String updatedName = itemNameEditText.getText().toString().trim();
            String updatedQuantity = itemQuantityEditText.getText().toString().trim();

            if (!updatedName.isEmpty() && !updatedQuantity.isEmpty()) {
                item.setItemName(updatedName);
                item.setItemQuantity(updatedQuantity);

                if (item.getItemId() != null) {
                    shoppingListRef.child(item.getItemId()).setValue(item)
                            .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Item updated successfully"))
                            .addOnFailureListener(e -> Log.d(DEBUG_TAG, "Error updating item: " + e.getMessage()));
                } else {
                    Log.d(DEBUG_TAG, "Error: itemId is null");
                }
            }
        });

        builder.setNegativeButton("Delete", (dialog, which) -> {
            if (item.getItemId() != null) {
                shoppingListRef.child(item.getItemId()).removeValue()
                        .addOnSuccessListener(aVoid -> Log.d(DEBUG_TAG, "Item deleted successfully"))
                        .addOnFailureListener(e -> Log.d(DEBUG_TAG, "Error deleting item: " + e.getMessage()));
            } else {
                Log.d(DEBUG_TAG, "Error: itemId is null");
            }
        });

        builder.setNeutralButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

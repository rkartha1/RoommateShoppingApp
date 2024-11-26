package edu.uga.cs.roommateshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MarkAsPurchasedActivity extends AppCompatActivity {

    private ListView shoppingListView;
    private ArrayList<ShoppingItem> shoppingList;
    private ArrayList<ShoppingItem> selectedItems;
    private Button markPurchasedButton;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_as_purchased);

        shoppingListView = findViewById(R.id.shopping_list_view);
        markPurchasedButton = findViewById(R.id.mark_purchased_button);

        Button goBackButton2 = findViewById(R.id.go_back_button2);
        goBackButton2.setOnClickListener(v -> {
            Intent intent = new Intent(MarkAsPurchasedActivity.this, RoommateManagementActivity.class);
            startActivity(intent);
        });

        shoppingList = new ArrayList<>();
        selectedItems = new ArrayList<>();

        // Fetch the shopping list from Firebase (this assumes the shopping list is already populated)
        fetchShoppingListFromFirebase();

        markPurchasedButton.setOnClickListener(view -> markItemsAsPurchased());
    }

    private void fetchShoppingListFromFirebase() {
        // Get the shopping list from Firebase
        DatabaseReference shoppingListRef = FirebaseDatabase.getInstance().getReference("shoppingList");

        shoppingListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shoppingList.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    ShoppingItem item = itemSnapshot.getValue(ShoppingItem.class);
                    if (item != null) {
                        item.setItemId(itemSnapshot.getKey());  // Set the item ID
                        shoppingList.add(item);
                    }
                }
                // Set up the ListView with checkboxes
                setupListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read error
            }
        });
    }

    private void setupListView() {
        adapter = new CustomAdapter(shoppingList);
        shoppingListView.setAdapter(adapter);
    }

    private class CustomAdapter extends BaseAdapter {
        private List<ShoppingItem> items;

        public CustomAdapter(List<ShoppingItem> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_list_item, parent, false);
            }

            ShoppingItem item = items.get(position);
            CheckBox checkBox = convertView.findViewById(R.id.item_checkbox);
            TextView itemName = convertView.findViewById(R.id.item_name);

            itemName.setText(item.getItemName() + " - " + item.getItemQuantity());

            checkBox.setOnCheckedChangeListener(null);
            checkBox.setChecked(selectedItems.contains(item));

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedItems.add(item);
                } else {
                    selectedItems.remove(item);
                }
            });

            return convertView;
        }
    }

    private void markItemsAsPurchased() {
        if (selectedItems.isEmpty()) {
            Toast.makeText(this, "Please select items", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current user (roommate) who made the purchase
        String currentUserId = "Roommate1";  // You can get this from Firebase Authentication or pass it through the intent

        // Create a reference to the "recently purchased" node in Firebase
        DatabaseReference recentlyPurchasedRef = FirebaseDatabase.getInstance().getReference("recentlyPurchased");

        // Add selected items to the "recently purchased" list
        for (ShoppingItem item : selectedItems) {
            // Set the user who purchased the item
            item.setPurchasedBy(currentUserId);

            // Move the item to the "recently purchased" list in Firebase
            recentlyPurchasedRef.push().setValue(item);
        }

        // Remove the selected items from the shopping list in Firebase
        DatabaseReference shoppingListRef = FirebaseDatabase.getInstance().getReference("shoppingList");
        for (ShoppingItem item : selectedItems) {
            shoppingListRef.child(item.getItemId()).removeValue();
        }

        // Provide feedback and clear the selection
        Toast.makeText(this, "Items marked as purchased", Toast.LENGTH_SHORT).show();
        selectedItems.clear();
        shoppingListView.clearChoices();
        fetchShoppingListFromFirebase(); // Reload the shopping list
    }
}

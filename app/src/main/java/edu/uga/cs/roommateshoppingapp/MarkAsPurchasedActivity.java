package edu.uga.cs.roommateshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MarkAsPurchasedActivity extends AppCompatActivity {

    private ListView shoppingListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<ShoppingItem> shoppingList;
    private ArrayList<ShoppingItem> selectedItems;
    private EditText totalPriceEditText;
    private Button markPurchasedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_as_purchased);

        shoppingListView = findViewById(R.id.shopping_list_view);
        totalPriceEditText = findViewById(R.id.total_price_edit_text);
        markPurchasedButton = findViewById(R.id.mark_purchased_button);

        shoppingList = new ArrayList<>();
        selectedItems = new ArrayList<>();

        // Fetch the shopping list from Firebase (this assumes the shopping list is already populated)
        fetchShoppingListFromFirebase();

        markPurchasedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markItemsAsPurchased();
            }
        });
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
        // Use a custom adapter to display shopping items with checkboxes
        ArrayList<String> itemNames = new ArrayList<>();
        for (ShoppingItem item : shoppingList) {
            itemNames.add(item.getItemName() + " - " + item.getItemQuantity());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, itemNames);
        shoppingListView.setAdapter(adapter);
        shoppingListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Set item click listener to track selected items
        shoppingListView.setOnItemClickListener((parent, view, position, id) -> {
            CheckBox checkBox = (CheckBox) view.findViewById(android.R.id.text1);
            if (checkBox.isChecked()) {
                selectedItems.add(shoppingList.get(position));
            } else {
                selectedItems.remove(shoppingList.get(position));
            }
        });
    }

    private void markItemsAsPurchased() {
        String totalPriceText = totalPriceEditText.getText().toString().trim();

        if (selectedItems.isEmpty() || totalPriceText.isEmpty()) {
            Toast.makeText(this, "Please select items and enter a total price", Toast.LENGTH_SHORT).show();
            return;
        }

        double totalPrice = Double.parseDouble(totalPriceText);

        // Get the current user (roommate) who made the purchase
        String currentUserId = "Roommate1";  // You can get this from Firebase Authentication or pass it through the intent

        // Create a reference to the "recently purchased" node in Firebase
        DatabaseReference recentlyPurchasedRef = FirebaseDatabase.getInstance().getReference("recentlyPurchased");

        // Add selected items to the "recently purchased" list
        for (ShoppingItem item : selectedItems) {
            item.setPrice(totalPrice / selectedItems.size());  // Distribute the total price among selected items
            item.setPurchasedBy(currentUserId);  // Record which roommate made the purchase

            // Move the item to the "recently purchased" list in Firebase
            recentlyPurchasedRef.push().setValue(item);
        }

        // Remove the selected items from the shopping list in Firebase
        DatabaseReference shoppingListRef = FirebaseDatabase.getInstance().getReference("shoppingList");
        for (ShoppingItem item : selectedItems) {
            shoppingListRef.child(item.getItemId()).removeValue();        }

        // Provide feedback and clear the selection
        Toast.makeText(this, "Items marked as purchased", Toast.LENGTH_SHORT).show();
        selectedItems.clear();
        totalPriceEditText.setText("");
        shoppingListView.clearChoices();
        adapter.notifyDataSetChanged();
    }
}

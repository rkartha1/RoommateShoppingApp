package edu.uga.cs.roommateshoppingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The NewItemActivity class allows users to add new items to the shopping list.
 *
 */
public class NewItemActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "NewItemActivity";

    private EditText itemNameEditText;
    private EditText itemQuantityEditText;
    private Button addItemButton;

    /**
     * Called when the activity is created.
     *
     * @param savedInstanceState A bundle containing data for the activity's state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        itemNameEditText = findViewById(R.id.editTextItemName);
        itemQuantityEditText = findViewById(R.id.editTextItemQuantity);
        addItemButton = findViewById(R.id.buttonAddItem);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToFirebase();
            }
        });
    }

    /**
     * Adds a new shopping item to the Firebase database.
     */
    private void addItemToFirebase() {
        String itemName = itemNameEditText.getText().toString().trim();
        String itemQuantity = itemQuantityEditText.getText().toString().trim();

        // Validate input
        if (itemName.isEmpty() || itemQuantity.isEmpty()) {
            Toast.makeText(this, "Please enter both item name and quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to Firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("shoppingList");
        String itemId = databaseReference.push().getKey();
        ShoppingItem shoppingItem = new ShoppingItem(itemName, itemQuantity);

        // Add item to Firebase
        if (itemId != null) {
            databaseReference.child(itemId).setValue(shoppingItem)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close the activity and return to the previous one
                        } else {
                            Log.d(DEBUG_TAG, "Failed to add item", task.getException());
                            Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}


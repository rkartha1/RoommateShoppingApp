package edu.uga.cs.roommateshoppingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Activity for signing in a user with email and password.
 */
public class SignInActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "SignInActivity";

    private EditText emailEditText;
    private EditText passwordEditText;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState the saved instance state if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize the EditText fields for email and password
        emailEditText = findViewById(R.id.editTextText2);
        passwordEditText = findViewById(R.id.editTextText3);

        // Set up the sign-in button click listener
        Button signInButton = findViewById(R.id.button4);
        signInButton.setOnClickListener(new SignInButtonClickListener());
    }

    /**
     * Inner class that handles the sign-in button click event.
     */
    private class SignInButtonClickListener implements View.OnClickListener {

        /**
         * Called when the sign-in button is clicked.
         * Attempts to sign in the user using the provided email and password.
         *
         * @param view the view that was clicked
         */
        @Override
        public void onClick(View view) {
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            // Attempt to sign in with email and password
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign-in success, navigate to RoommateManagementActivity
                                Log.d(DEBUG_TAG, "signInWithEmail:success");
                                Intent intent = new Intent(SignInActivity.this, RoommateManagementActivity.class);
                                startActivity(intent);
                            } else {
                                // Sign-in failed, display a message to the user
                                Log.d(DEBUG_TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}

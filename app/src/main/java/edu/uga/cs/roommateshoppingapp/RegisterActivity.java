package edu.uga.cs.roommateshoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Activity for registering a new user in the app using Firebase Authentication.
 */
public class RegisterActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "RegisterActivity";

    private EditText emailEditText;
    private EditText passworEditText;

    /**
     * Initializes the RegisterActivity by setting up the UI elements and click listener for the register button.
     *
     * @param savedInstanceState The saved instance state, if any, to restore the previous state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById( R.id.editText );
        passworEditText = findViewById( R.id.editText5 );

        Button registerButton = findViewById(R.id.button3);
        registerButton.setOnClickListener( new RegisterButtonClickListener() );
    }

    /**
     * Listener for the register button click. Handles the process of creating a new user using Firebase Authentication.
     */
    private class RegisterButtonClickListener implements View.OnClickListener {
        /**
         * Called when the register button is clicked. Creates a new user using Firebase authentication.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            final String email = emailEditText.getText().toString();
            final String password = passworEditText.getText().toString();

            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            // Create a new user using the email and password provided
            firebaseAuth.createUserWithEmailAndPassword( email, password )
                    .addOnCompleteListener( RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        /**
                         * Called when the registration task is complete. If the task is successful,
                         * the user is redirected to the Roommate Management Activity. If it fails,
                         * an error message is shown.
                         *
                         * @param task The task of the registration process.
                         */
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText( getApplicationContext(),
                                        "Registered user: " + email,
                                        Toast.LENGTH_SHORT ).show();

                                // Registration success, log the event
                                Log.d( DEBUG_TAG, "createUserWithEmail: success" );

                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                // Redirect to the Roommate Management Activity
                                Intent intent = new Intent( RegisterActivity.this, RoommateManagementActivity.class );
                                startActivity( intent );

                            } else {
                                // Registration failed, show an error message
                                Log.w(DEBUG_TAG, "createUserWithEmail: failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}


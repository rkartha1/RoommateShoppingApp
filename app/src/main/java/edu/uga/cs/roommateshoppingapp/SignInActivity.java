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

public class SignInActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "SignInActivity";

    private EditText emailEditText;
    private EditText passwordEditText;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        emailEditText = findViewById(R.id.editTextText2);
        passwordEditText = findViewById(R.id.editTextText3);

        Button signInButton = findViewById(R.id.button4);

        signInButton.setOnClickListener( new SignInButtonClickListener() );

    }

    private class SignInButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();


            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


            firebaseAuth.signInWithEmailAndPassword( email, password )
                    .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d( DEBUG_TAG, "signInWithEmail:success" );
                                //FirebaseUser user = mAuth.getCurrentUser();
                                // after a successful sign in, start the job leads management activity
                                Intent intent = new Intent( SignInActivity.this, RoommateManagementActivity.class );
                                startActivity( intent );
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Log.d( DEBUG_TAG, "signInWithEmail:failure", task.getException() );
                                Toast.makeText( SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
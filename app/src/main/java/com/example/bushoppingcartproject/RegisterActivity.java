package com.example.bushoppingcartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button registerFinalButton;
    private EditText registerUsernameInput;
    private ProgressDialog loadingBar;
    private Shelf createStoreShelf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createStoreShelf = new Shelf();
        registerFinalButton = findViewById(R.id.registerFinalButton);
        registerUsernameInput = findViewById(R.id.registerUsernameInput);
        loadingBar = new ProgressDialog(this);

        registerFinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount() {
        String username = registerUsernameInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please provide a username", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Registering new user");
            loadingBar.setMessage("Loading...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUsername(username);
        }
    }

    // Validate existence of user in the firebase database based on the username
    private void ValidateUsername(final String username) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(username).exists())) {
                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("username", username);


                    userRef.child("Users").child(username).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Registration Complete!", Toast.LENGTH_SHORT).show();

                                // populate store shelf with all items in the database
                                createStoreShelf.createStoreCatalog(username);

                                Intent sendToLoginPage = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(sendToLoginPage);
                            }
                            else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "An error has occurred... Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "this username " + username + " already exists, please choose another.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                throw databaseError.toException();
            }
        });
    }
}


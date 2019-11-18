package com.example.bushoppingcartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bushoppingcartproject.UserData.DisplayName;
import com.example.bushoppingcartproject.UserData.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button loginFinalButton;
    private EditText loginUsernameInput;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginFinalButton = findViewById(R.id.loginFinalButton);
        loginUsernameInput = findViewById(R.id.loginUsernameInput);

        loginFinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = loginUsernameInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please provide a username", Toast.LENGTH_SHORT).show();
        }

        else{
//            Intent loginIntent = new Intent(LoginActivity.this, ShoppingActivity.class);
//            startActivity(loginIntent);
            AllowAccessToAccount(username);
        }

    }

    private void AllowAccessToAccount(final String username) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference RootRef = database.getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(username).exists()) {
                    //get value of username
                    Users userData = dataSnapshot.child("Users").child(username).getValue(Users.class);

                    if (userData.username.equals(username)) {
                        // send user into the app main page (ShoppingActivity)

                        Intent loginIntent = new Intent(LoginActivity.this, ShoppingActivity.class);
                        startActivity(loginIntent);

                        // Display message welcoming the user at the bottom of the page
                        Toast.makeText(LoginActivity.this, "Welcome " + username, Toast.LENGTH_SHORT).show();

                        // saves the username in a another class in order to be accessed later by the ShoppingActivity profileName
                        DisplayName.currentUser = userData;
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this, "Account with the username " + username + " doesn't exist. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value

            }
        });
    }
}


package com.example.bushoppingcartproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.bushoppingcartproject.StoreShelf.Item;
import com.example.bushoppingcartproject.UserData.DisplayName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PriorityActivity extends AppCompatActivity {

    private TextView itemName;
    private TextView itemPrice;
    private ImageView itemImage;
    private Button addToCartButton;
    private NumberPicker priorityPicker;

    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority);

        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        itemImage = findViewById(R.id.itemImage);
        addToCartButton = findViewById(R.id.addToCartButton);

        //set min and max values for the priority picker
        priorityPicker = findViewById(R.id.priorityPicker);
        priorityPicker.setMinValue(1);
        priorityPicker.setMaxValue(7);



        // Receive intent data from StoreFragment
        selectedItem = getIntent().getStringExtra("itemName");

        getItemDetails(selectedItem);

        // addToCartButton click listener
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToCart();

            }
        });
    }

    private void addItemToCart() {
        // Create a cart list for the current user
        String currentUser = DisplayName.currentUser.username;
        final String currentUserCart = currentUser + "-Cart";
        final DatabaseReference userCartList = FirebaseDatabase.getInstance().getReference().child(currentUserCart).child(selectedItem);


        userCartList.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child(currentUserCart).child(selectedItem).exists())) {
                    final HashMap<String, Object> cartMap = new HashMap<>();
                    cartMap.put("name", itemName.getText().toString());
                    cartMap.put("price", itemPrice.getText().toString());
                    cartMap.put("priority", String.valueOf(priorityPicker.getValue()));

                    userCartList.updateChildren(cartMap);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                throw databaseError.toException();
            }
        });

        Intent backToStore =  new Intent(this, ShoppingActivity.class);
        startActivity(backToStore);
    }

    private void getItemDetails(String selectedItem) {
        DatabaseReference itemRef = FirebaseDatabase.getInstance().getReference().child("Shelf");

        itemRef.child(selectedItem).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Item item = dataSnapshot.getValue(Item.class);

                    itemName.setText(item.getName());
                    itemPrice.setText(String.valueOf(item.getPrice()));
                    itemImage.setImageResource(item.getImage());
                    item.setPriority(priorityPicker.getValue());
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

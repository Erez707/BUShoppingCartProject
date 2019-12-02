package com.example.bushoppingcartproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bushoppingcartproject.StoreShelf.Item;
import com.example.bushoppingcartproject.UserData.DisplayName;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class CartFragment extends Fragment {

    public CartFragment() {
        // empty constructor needed for Firebase
    }

    private String currentUserCart;
    private DatabaseReference CartReference;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private String currentUser;

    private Button checkoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cartView = inflater.inflate(R.layout.fragment_cart, container, false);

        checkoutButton = cartView.findViewById(R.id.checkoutButton);


        currentUser = DisplayName.currentUser.username;
        currentUserCart = currentUser + "-Cart";
        CartReference = FirebaseDatabase.getInstance().getReference().child(currentUserCart);
        CartReference.keepSynced(true);

        recyclerView = cartView.findViewById(R.id.cart_recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToOrdersFragment();
                // Clear cart fragment
                CartReference.removeValue();
            }
        });

        return cartView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(CartReference.orderByChild("priority"), Item.class).build();

        FirebaseRecyclerAdapter<Item, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Item, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull final Item model) {
                //display the data in the cart_items_layout cardview
                holder.cartItemName.setText(model.getName());
                holder.cartItemPrice.setText(String.valueOf(model.getPrice()));
                holder.cartItemImage.setImageResource(model.getImage());
                holder.cartItemPriority.setText(String.valueOf(model.getPriority()));

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                return new CartViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    private void addItemToOrdersFragment() {
        // iterate through items in cart and create an orders list for the user

        final String currentUserOrders = currentUser + "-Orders";
        final DatabaseReference OrdersReference = FirebaseDatabase.getInstance().getReference().child(currentUserOrders);

        CartReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child(currentUserOrders).exists())) {
                    Iterator<DataSnapshot> itemsInCart = dataSnapshot.getChildren().iterator();

                    while (itemsInCart.hasNext()) {
                        DataSnapshot itemInCart = itemsInCart.next();
                        Item cartItem = itemInCart.getValue(Item.class);
                        OrdersReference.child(cartItem.getName()).setValue(cartItem);
                    }
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

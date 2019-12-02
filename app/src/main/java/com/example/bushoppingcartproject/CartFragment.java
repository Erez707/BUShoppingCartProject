package com.example.bushoppingcartproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bushoppingcartproject.StoreShelf.Item;
import com.example.bushoppingcartproject.UserData.DisplayName;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartFragment extends Fragment {

    public CartFragment() {
        // empty constructor needed for Firebase
    }

    private String currentUserCart;
    private DatabaseReference CartReference;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

//    private String selectedItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View cartView = inflater.inflate(R.layout.fragment_cart, container, false);

        // Receive intent data from StoreFragment
//        selectedItem = getActivity().getIntent().getStringExtra("itemName");  //  I HOPE THIS WORKS...NOT SURE YET!!!!!!!!!!!

        String currentUser = DisplayName.currentUser.username;
        currentUserCart = currentUser + "-Cart";
        CartReference = FirebaseDatabase.getInstance().getReference().child(currentUserCart);  // my not need teh selected item part!!!!!!!!!!!!
        CartReference.keepSynced(true);

        recyclerView = cartView.findViewById(R.id.cart_recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

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
}

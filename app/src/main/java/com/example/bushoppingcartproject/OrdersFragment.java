package com.example.bushoppingcartproject;

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

public class OrdersFragment extends Fragment {

    public OrdersFragment() {
        // empty constructor needed for Firebase
    }

    private String currentUserOrders;
    private DatabaseReference OrdersReference;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ordersView = inflater.inflate(R.layout.fragment_orders, container, false);


        String currentUser = DisplayName.currentUser.username;
        currentUserOrders = currentUser + "-Orders";
        OrdersReference = FirebaseDatabase.getInstance().getReference().child(currentUserOrders);
        OrdersReference.keepSynced(true);

        recyclerView = ordersView.findViewById(R.id.orders_recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return ordersView;
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(OrdersReference.orderByChild("priority"), Item.class).build();

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

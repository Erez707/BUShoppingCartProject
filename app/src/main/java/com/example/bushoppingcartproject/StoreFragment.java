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

import com.example.bushoppingcartproject.UserData.DisplayName;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StoreFragment extends Fragment {

    public StoreFragment() {
        // empty constructor needed for Firebase
    }


    private DatabaseReference ShelfReference;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View storeView = inflater.inflate(R.layout.fragment_store, container, false);
        ShelfReference = FirebaseDatabase.getInstance().getReference().child("User").child(DisplayName.currentUser.username).child("Shelf");

        recyclerView = storeView.findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);                      // this may not be true because I want to be able to change things per user,,,decide later
        layoutManager = new LinearLayoutManager(getActivity());  // MAYBE CHANGE TO GRID LAYOUT MANAGER (try different things out)
        recyclerView.setLayoutManager(layoutManager);

        return storeView;

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Item> options = new FirebaseRecyclerOptions.Builder<Item>().setQuery(ShelfReference, Item.class).build();

        FirebaseRecyclerAdapter<Item, ItemViewHolder> adapter = new FirebaseRecyclerAdapter<Item, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {
                //display the data in the store_items_layout cardview
                holder.itemName.setText(model.getName());
                holder.itemPrice.setText(String.valueOf(model.getPrice()));

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_items_layout, parent, false);
                return new ItemViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}

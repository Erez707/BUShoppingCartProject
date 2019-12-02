package com.example.bushoppingcartproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView cartItemName, cartItemPrice, cartItemPriority;
    public ImageView cartItemImage;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        cartItemPriority = itemView.findViewById(R.id.cartItemPriority);
        cartItemName = itemView.findViewById(R.id.cartItemName);
        cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
        cartItemImage = itemView.findViewById(R.id.cartItemImage);

    }

}

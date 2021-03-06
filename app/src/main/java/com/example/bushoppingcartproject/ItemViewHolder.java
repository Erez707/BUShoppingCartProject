package com.example.bushoppingcartproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {


    public TextView itemName, itemPrice;
    public ImageView itemImage;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        itemImage = itemView.findViewById(R.id.itemImage);

    }
}

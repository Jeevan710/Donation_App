package com.example.donationapp.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.Interface.ItemClickListener;
import com.example.donationapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription,txtProductPhone;
    public ImageView imageView;
    public ItemClickListener listner;


    public ProductViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPhone=(TextView) itemView.findViewById(R.id.product_pphone);
    }

    public void setItemClickListner(ItemClickListener listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}


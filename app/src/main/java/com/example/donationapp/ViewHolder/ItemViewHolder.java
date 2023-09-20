package com.example.donationapp.ViewHolder;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.Interface.ItemClickListener;
import com.example.donationapp.R;



public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductDescription,txtProductStatus;
    public ImageView imageView;
    public ItemClickListener listner;


    public ItemViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_d_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_d_name);
        txtProductStatus = (TextView) itemView.findViewById(R.id.product_d_state);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_d_description);
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

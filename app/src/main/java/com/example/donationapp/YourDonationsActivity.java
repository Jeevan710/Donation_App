package com.example.donationapp;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donationapp.Model.Products;
import com.example.donationapp.Prevalent.Prevalent;
import com.example.donationapp.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class YourDonationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unVerifiedProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_donations);

        unVerifiedProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");

        recyclerView=findViewById(R.id.donate_home_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>().setQuery(unVerifiedProductsRef.orderByChild("donatePhone").equalTo(Prevalent.currentOnlineUser.getPhone()),Products.class).build();
        FirebaseRecyclerAdapter<Products, ItemViewHolder> adapter=new FirebaseRecyclerAdapter<Products, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Products model) {
                holder.txtProductName.setText(model.getPname());
                holder.txtProductDescription.setText(model.getDescription());
                holder.txtProductStatus.setText(model.getProductState());
                Picasso.get().load(model.getImage()).into(holder.imageView);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String productID=model.getPid();
                        CharSequence options[]=new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder builder=new AlertDialog.Builder(YourDonationsActivity.this);
                        builder.setTitle("Do you want to delete this product");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int position)
                            {
                                if(position==0)
                                {
                                    DeleteProduct(productID);
                                }
                                if(position==1)
                                {

                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_items_layout, parent, false);
                ItemViewHolder holder = new ItemViewHolder(view);
                return holder;
            }
        } ;
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void DeleteProduct(String productID)
    {
        unVerifiedProductsRef.child(productID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(YourDonationsActivity.this,"Item Deleted",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
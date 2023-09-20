package com.example.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductsActivity extends AppCompatActivity {
    private Button applyChangesBtn,deleteBtn;
    private EditText Mname,Mdescription;
    private ImageView Mimageview;
    private String productID="";
    private DatabaseReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);

        productID=getIntent().getStringExtra("pid");
        productRef= FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        applyChangesBtn=findViewById(R.id.maintain_apply_changes_btn);
        Mname=findViewById(R.id.maintain_product_name);
        Mdescription=findViewById(R.id.maintain_product_description);
        Mimageview=findViewById(R.id.maintain_product_image);
        deleteBtn=findViewById(R.id.maintain_delete_products_btn);

        displaySpecificProductsInfo();

        applyChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteThisProduct();
            }
        });
    }

    private void deleteThisProduct()
    {
        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(AdminMaintainProductsActivity.this,"Product Deleted Successfully",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void applyChanges()
    {
        String pName=Mname.getText().toString();
        String pDescription=Mdescription.getText().toString();
        if(pName.equals(""))
        {
            Toast.makeText(this,"Write Product Name",Toast.LENGTH_SHORT).show();
        }
        else  if(pDescription.equals(""))
        {
            Toast.makeText(this,"Write Product Description",Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", pDescription);
            productMap.put("pname", pName);

            productRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainProductsActivity.this,"Changes Applied Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AdminMaintainProductsActivity.this, AdminHomeActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }
            });
        }
    }

    private void displaySpecificProductsInfo() {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot)
            {
                if(datasnapshot.exists())
                {
                    String pName=datasnapshot.child("pname").getValue().toString();
                    String pDescription=datasnapshot.child("description").getValue().toString();
                    String pImage=datasnapshot.child("image").getValue().toString();

                    Mname.setText(pName);
                    Mdescription.setText(pDescription);
                    Picasso.get().load(pImage).into(Mimageview);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.donationapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {
    private Button LogoutBtn,MaintainProductsBtn,checkApproveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);



        LogoutBtn=(Button) findViewById(R.id.admin_logout_btn);
        MaintainProductsBtn=(Button)findViewById(R.id.maintain_products_btn);
        checkApproveBtn=(Button)findViewById(R.id.check_approve_btn);

        MaintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this,HomeActivity.class);
                intent.putExtra("Admins","Admins");
                startActivity(intent);

            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        checkApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminHomeActivity.this,AdminCheckAndApproveActivity.class);
                startActivity(intent);
            }
        });
    }
}
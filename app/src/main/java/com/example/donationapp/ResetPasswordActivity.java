package com.example.donationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.donationapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private String check="";
    private TextView pageTitle,TitleQuestions;
    private EditText Phoneno,Question1,Question2;
    private Button verifyBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check=getIntent().getStringExtra("check");
        pageTitle=findViewById(R.id.reset_settings);
        TitleQuestions=findViewById(R.id.title_questions);
        Phoneno=findViewById(R.id.find_phone_number);
        Question1=findViewById(R.id.question_1);
        Question2=findViewById(R.id.question_2);
        verifyBtn=findViewById(R.id.verify_btn);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        Phoneno.setVisibility(View.GONE);

        if(check.equals("settings"))
        {
            pageTitle.setText("Set Questions");
            TitleQuestions.setText("Please Set Answers for the following questions");
            verifyBtn.setText("Set");
            displayPreviousAnswers();

            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    setAnswers();
                }
            });
        }
        else if (check.equals("login"))
        {
            Phoneno.setVisibility(View.VISIBLE);
            verifyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyUser();
                }
            });
        }
    }

    private void verifyUser()
    {
        final String phone=Phoneno.getText().toString();
        final String answer1=Question1.getText().toString().toLowerCase();
        final String answer2=Question2.getText().toString().toLowerCase();

        if(!phone.equals("") && !answer1.equals("") && !answer2.equals(""))
        {
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(phone);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if(snapshot.exists())
                    {
                        String mPhone=snapshot.child("phone").getValue().toString();
                        if(snapshot.hasChild("Security Questions"))
                        {
                            String ans1=snapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2=snapshot.child("Security Questions").child("answer2").getValue().toString();
                            if(!ans1.equals(answer1))
                            {
                                Toast.makeText(ResetPasswordActivity.this,"Wrong Answer",Toast.LENGTH_SHORT).show();
                            }
                            else if(!ans2.equals(answer2))
                            {
                                Toast.makeText(ResetPasswordActivity.this,"Wrong Answer",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                AlertDialog.Builder builder=new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("New Password");

                                final EditText newPassword=new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Write new Password here");
                                builder.setView(newPassword);
                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(!newPassword.getText().toString().equals(""))
                                        {
                                            ref.child("password").setValue(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(ResetPasswordActivity.this,"Password Changed Successfully",Toast.LENGTH_SHORT).show();
                                                        Intent intent=new Intent(ResetPasswordActivity.this,LoginActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                    }
                                });

                                builder.show();
                            }
                        }
                        else
                        {
                            Toast.makeText(ResetPasswordActivity.this,"Security Questions not set",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ResetPasswordActivity.this, "Phone Number not exist", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else
        {
            Toast.makeText(ResetPasswordActivity.this, "Fill all details", Toast.LENGTH_SHORT).show();
        }


    }

    private void setAnswers()
    {
        String answer1=Question1.getText().toString().toLowerCase();
        String answer2=Question2.getText().toString().toLowerCase();

        if(Question1.equals("") && Question2.equals(""))
        {
            Toast.makeText(ResetPasswordActivity.this,"Please answer both questions",Toast.LENGTH_SHORT).show();
        }
        else
        {
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("answer1", answer1);
            userdataMap.put("answer2", answer2);
            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(ResetPasswordActivity.this,"Security Answers set successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ResetPasswordActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }

    private void displayPreviousAnswers()
    {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(  snapshot.exists()  )
                {
                    String ans1=snapshot.child("answer1").getValue().toString();
                    String ans2=snapshot.child("answer2").getValue().toString();
                    Question1.setText(ans1);
                    Question2.setText(ans2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
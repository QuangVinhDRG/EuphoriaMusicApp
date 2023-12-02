package com.example.euphoriamusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euphoriamusicapp.data.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.e;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ResetPasswordEmailActivity extends AppCompatActivity {
    private Button btnConfirm;
    private TextView tvBackToLogin;
    private EditText edtEmail;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_email);
        btnConfirm = findViewById(R.id.btnConfirmEmail);
        tvBackToLogin = findViewById(R.id.tvBackEmail);
        edtEmail = findViewById(R.id.edtEmail);
        mAuth = FirebaseAuth.getInstance();
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resetpassword();
            }
        });
        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordEmailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkinputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void Resetpassword() {
        if(Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();
            databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean Ischeck = false;
                    for ( DataSnapshot data : snapshot.getChildren()) {
                        user tuancho =  data.getValue(user.class);
                        if(tuancho.getEmail()==edtEmail.getText().toString())
                        {
                            Ischeck = true;
                            break;
                        }
                    }
                    if( Ischeck){
                        mAuth.sendPasswordResetEmail(edtEmail.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Intent intent = new Intent(ResetPasswordEmailActivity.this,ResetPasswordSuccessActivity.class);
                                        startActivity(intent);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ResetPasswordEmailActivity.this, "Your email does not exist", Toast.LENGTH_SHORT).show();
                                        btnConfirm.setEnabled(true);
                                    }
                                });
                    }else {
                        edtEmail.setError("Invalid Email Pattern!");
                        btnConfirm.setEnabled(true);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }

    private void checkinputs() {
        if(!edtEmail.getText().toString().isEmpty()){
            btnConfirm.setEnabled(true);
        }else {
            btnConfirm.setEnabled(false);
        }
    }
}
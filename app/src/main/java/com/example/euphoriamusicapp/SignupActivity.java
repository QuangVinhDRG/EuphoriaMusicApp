package com.example.euphoriamusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    ImageButton btnBack;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmpassword;
    private  ImageButton ibHideShowpass1,ibHideShowpass2;


    private Button btn_SinUp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnBack = findViewById(R.id.btnBack);
        btn_SinUp = findViewById(R.id.btnSignUp);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        ibHideShowpass1 = findViewById(R.id.ibShowHidePassword1);
        ibHideShowpass2 = findViewById(R.id.ibShowHideRePassword2);
        confirmpassword = findViewById(R.id.confirmpassword);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ibHideShowpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibHideShowpass1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(password.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            ibHideShowpass1.setImageResource(R.drawable.hide_password_24);
                        }else{
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            ibHideShowpass1.setImageResource(R.drawable.show);
                        }
                    }
                });
            }
        });
        ibHideShowpass2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibHideShowpass2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(confirmpassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                            confirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            ibHideShowpass2.setImageResource(R.drawable.show);
                        }else{
                            confirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            ibHideShowpass2.setImageResource(R.drawable.hide_password_24);
                        }
                    }
                });
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btn_SinUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpwithFireBase();


            }
        });
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void SignUpwithFireBase() {
        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            if(password.getText().toString().equals(confirmpassword.getText().toString())){
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())

                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    HashMap<String,Object> map = new HashMap<>();
                                    map.put("id",user.getUid());
                                    map.put("name",username.getText().toString());
                                    map.put("email",user.getEmail());
                                    map.put("profile",getString(R.string.avatar_default));
                                    database.getReference().child("users").child(user.getUid()).setValue(map);
                                    Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(SignupActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    btn_SinUp.setEnabled(true);

                                }
                            }
                        });
            }else {
                confirmpassword.setError("Password doesn't match.");
                btn_SinUp.setEnabled(true);
            }

        }else {
            email.setError("Invalid Email Pattern!");
            btn_SinUp.setEnabled(true);
        }
    }

    private void CheckInputs() {
        if(!username.getText().toString().isEmpty()){
            if(!email.getText().toString().isEmpty()){
                if(!password.getText().toString().isEmpty()){
                    if(!confirmpassword.getText().toString().isEmpty()){
                        btn_SinUp.setEnabled(true);
                        btn_SinUp.setTextColor(getResources().getColor(R.color.white));
                    }else {
                        btn_SinUp.setEnabled(false);
                        btn_SinUp.setTextColor(getResources().getColor(R.color.transWite));
                    }
                }else {
                    btn_SinUp.setEnabled(false);
                    btn_SinUp.setTextColor(getResources().getColor(R.color.transWite));
                }
            }else {
                btn_SinUp.setEnabled(false);
                btn_SinUp.setTextColor(getResources().getColor(R.color.transWite));
            }
        }else {
            btn_SinUp.setEnabled(false);
            btn_SinUp.setTextColor(getResources().getColor(R.color.transWite));
        }
    }
}
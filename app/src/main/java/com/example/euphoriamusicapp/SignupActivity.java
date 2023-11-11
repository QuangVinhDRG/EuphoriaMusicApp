package com.example.euphoriamusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    ImageButton btnBack;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmpassword;

    private Button btn_SinUp;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnBack = findViewById(R.id.btnBack);
        btn_SinUp = findViewById(R.id.btnSignUp);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

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
        if(!email.getText().toString().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@+^[a-zA-Z0-9.-]+$")){
            if(password.getText().toString().equals(confirmpassword.getText().toString())){
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())

                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(SignupActivity.this,SliderActivity.class);
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
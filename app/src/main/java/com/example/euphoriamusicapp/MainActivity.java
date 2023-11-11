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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    TextView tvSignUp, tvForgotPassword;

    EditText edtEmail,editPass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        editPass = findViewById(R.id.edtpass);
        mAuth = FirebaseAuth.getInstance();
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResetPasswordEmailActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                SignInWithFirebase();
            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void SignInWithFirebase() {
        if(!edtEmail.getText().toString().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@+^[a-zA-Z0-9.-]+$")){
            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),editPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this,SliderActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Login failed.",
                                        Toast.LENGTH_SHORT).show();
                                btnLogin.setEnabled(true);
                            }
                        }
                    });

        }else {
            edtEmail.setError("Invalid Email Pattern!");
            btnLogin.setEnabled(true);
        }
    }

    private void checkInputs() {
        if(!edtEmail.getText().toString().isEmpty()){
            if(!editPass.getText().toString().isEmpty()){
                btnLogin.setEnabled(true);
            }else {
                btnLogin.setEnabled(false);
            }
        }else {
            btnLogin.setEnabled(false);
        }
    }
}
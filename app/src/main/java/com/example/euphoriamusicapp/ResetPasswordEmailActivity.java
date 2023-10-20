package com.example.euphoriamusicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResetPasswordEmailActivity extends AppCompatActivity {
    private Button btnConfirm;
    private TextView tvBackToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_email);
        btnConfirm = findViewById(R.id.btnConfirmEmail);
        tvBackToLogin = findViewById(R.id.tvBackEmail);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordEmailActivity.this, ResetPasswordNewActivity.class);
                startActivity(intent);
            }
        });
        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordEmailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
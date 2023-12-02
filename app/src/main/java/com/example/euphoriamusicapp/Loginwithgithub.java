package com.example.euphoriamusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Loginwithgithub extends AppCompatActivity {
    EditText email;
    Button btnLogin;
    FirebaseAuth mAuth;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginwithgithub);
        email = findViewById(R.id.edtEmail);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                    // Target specific email with login hint.
                    provider.addCustomParameter("login", email.getText().toString());
                    // Request read access to a user's email addresses.
// This must be preconfigured in the app's API permissions.
                    List<String> scopes =
                            new ArrayList<String>() {
                                {
                                    add("user:email");
                                }
                            };
                    provider.setScopes(scopes);

                    Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
                    if (pendingResultTask != null) {
                        // There's something already here! Finish the sign-in for your user.
                        pendingResultTask
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {

                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle failure.
                                            }
                                        });
                    } else {
                        mAuth
                                .startActivityForSignInWithProvider(/* activity= */ Loginwithgithub.this, provider.build())
                                .addOnSuccessListener(
                                        new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {

                                                Intent intent = new Intent(Loginwithgithub.this,SliderActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                .addOnFailureListener(
                                        new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle failure.

                                            }
                                        });
                    }
                }else {
                    email.setError("Invalid Email Pattern!");
                    btnLogin.setEnabled(true);
                }
            }
        });
    }
}
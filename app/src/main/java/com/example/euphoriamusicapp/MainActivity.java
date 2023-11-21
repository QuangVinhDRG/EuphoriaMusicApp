package com.example.euphoriamusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;

    ImageView btnloginGG,btnloginFB,btnloginGitHub;
    TextView tvSignUp, tvForgotPassword;

    EditText edtEmail,editPass;
    private FirebaseAuth mAuth;
    private static  final int RC_SIGN_IN = 11;
    private static final String TAG  = "GOOGLEAUTH";

    private GoogleSignInClient mClient;
    private FirebaseDatabase database;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        edtEmail = findViewById(R.id.edtEmail);
        editPass = findViewById(R.id.edtpass);

        btnloginGG = findViewById(R.id.btnLoginWithGoogle);
        btnloginFB = findViewById(R.id.btnLoginWithFB);
        btnloginGitHub = findViewById(R.id.btnLoginWithApple);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);

        //Auth
        mAuth = FirebaseAuth.getInstance();

        //CONFIGURE GG
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mClient =  GoogleSignIn.getClient(this,gso);

        //database
        database = FirebaseDatabase.getInstance();

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
        btnloginGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     SignInWithFirebaseByGoogle();
            }
        });
        btnLogin.setEnabled(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

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

    private void SignInWithFirebaseByGoogle() {
        Intent SignInGGIntent = mClient.getSignInIntent();
        startActivityForResult(SignInGGIntent,RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mClient != null){
            mClient.signOut();}
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
                mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("id",user.getUid());
                            map.put("name",user.getDisplayName());
                            map.put("email",user.getEmail());
                            map.put("profile",user.getPhotoUrl().toString());
                            database.getReference().child("users").child(user.getUid()).setValue(map);
                            Intent intent = new Intent(MainActivity.this,SliderActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } catch (ApiException e) {
                e.printStackTrace();
                Log.e("GoogleSignIn", "Error code: " + e.getStatusCode());
            }

        }

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
package com.example.euphoriamusicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euphoriamusicapp.Admin.UpLoadSong;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;

    ImageView btnloginGG,btnloginGitHub,btnloginfb;
    TextView tvSignUp, tvForgotPassword;
    ImageButton ibShowHidePassword;

    EditText edtEmail,editPass;
    protected FirebaseAuth mAuth;
    private static  final int RC_SIGN_IN = 11;
    private static final String TAG  = "GOOGLEAUTH12";

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
        btnloginGitHub = findViewById(R.id.btnLoginWithGithub);
        btnloginfb = findViewById(R.id.btnLoginWithfb);
        ibShowHidePassword = findViewById(R.id.ibShowHidePassword);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);


        //Auth
        mAuth = FirebaseAuth.getInstance();

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //CONFIGURE GG
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mClient =  GoogleSignIn.getClient(this,gso);

        //database
        database = FirebaseDatabase.getInstance();
        ibShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editPass.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())) {
                    editPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ibShowHidePassword.setImageResource(R.drawable.hide_password_24);
                }else{
                    editPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ibShowHidePassword.setImageResource(R.drawable.show);
                }
            }
        });


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
        btnloginGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Loginwithgithub.class);
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
        btnloginfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FacebookAuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
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
        if(Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()){
            mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),editPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference databaseReference = firebaseDatabase.getReference();
                                FirebaseUser user = mAuth.getCurrentUser();
                                databaseReference.child("users").child(user.getUid()).child("isAdmin").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Long i = snapshot.getValue(Long.class);
                                        if(i == 1){
                                            Intent intent = new Intent(MainActivity.this, UpLoadSong.class);
                                            startActivity(intent);
                                        }else{
                                            Intent intent = new Intent(MainActivity.this,SliderActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });

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
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
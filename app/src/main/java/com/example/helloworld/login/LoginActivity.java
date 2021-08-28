package com.example.helloworld.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helloworld.R;
import com.example.helloworld.common.feed.FeedActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText mEmail, mPassword;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private Button mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.login_email);
        mPassword  = findViewById(R.id.activity_login__password);

        mLogin = findViewById(R.id.activity_login_button);

        mAuth = FirebaseAuth.getInstance();

        authListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null){
                Intent intent = new Intent(LoginActivity.this, FeedActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        };

        mLogin.setOnClickListener(this::onLogin);

    }

    private void onLogin(View view) {
        final  String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        if(invalidForm(email,password)){
            Snackbar.make(view,R.string.form_error,Snackbar.LENGTH_SHORT).show();
            return;
        }
        //validade de login
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this,task ->{
            if(!task.isSuccessful()){
                Toast.makeText(LoginActivity.this,R.string.sing_in_error,Toast.LENGTH_SHORT).show();
                return;
            }
            Intent in = new Intent(LoginActivity.this, FeedActivity.class);
            startActivity(in);

        });


        //register login in sql

        // move to the next page

    }

    private Boolean invalidForm(String email, String password) {
        return email.isEmpty() || password.isEmpty();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }

}

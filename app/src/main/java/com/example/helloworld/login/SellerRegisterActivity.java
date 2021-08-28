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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerRegisterActivity extends AppCompatActivity {
    private String type = "SELLER";
    private EditText mEmail, mPassword, mPasswordConfirm, mName;
    private Button mRegistration;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        mEmail = (EditText) findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPasswordConfirm = findViewById(R.id.passwordConfirm);
        mName = findViewById(R.id.name);

        mRegistration = findViewById(R.id.registrate);

        mRegistration.setOnClickListener(this::onRegister);
        mAuth = FirebaseAuth.getInstance();

        authListener = firebaseAuth -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Intent intent = new Intent(SellerRegisterActivity.this, FeedActivity.class);
                startActivity(intent);
                finish();
                return;
            }

        };


    }

    private void onRegister(View view) {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();
        final String passwordConfirm = mPasswordConfirm.getText().toString();
        final String name = mName.getText().toString();

        if (validForm(email, password, passwordConfirm, name)) {
            Snackbar.make(view, R.string.form_error, Snackbar.LENGTH_SHORT).show();
            return;
        }
        //Register User
        Task<AuthResult> userListener = mAuth.createUserWithEmailAndPassword(email, password);

        userListener.addOnCompleteListener(SellerRegisterActivity.this, task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(SellerRegisterActivity.this, R.string.sing_up_error, Toast.LENGTH_SHORT).show();
                return;
            }
            final FirebaseUser user = mAuth.getCurrentUser();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child("Seller").child(user.getUid());
            db.setValue(true).addOnCompleteListener(saved -> {
                if (!saved.isSuccessful()) {
                    user.delete();
                    Toast.makeText(SellerRegisterActivity.this, R.string.sing_up_error, Toast.LENGTH_LONG).show();

                }
                Toast.makeText(SellerRegisterActivity.this, R.string.sing_up_success, Toast.LENGTH_SHORT).show();
                Intent in = new Intent(SellerRegisterActivity.this, FeedActivity.class);
                startActivity(in);
                finish();
            });

        });


        // IN Success redirect to login

    }

    private Boolean validForm(String email, String password, String passwordConfirm, String name) {
        return email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || !password.equals(passwordConfirm) || name.isEmpty();
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
package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helloworld.login.ClientRegisterActivity;
import com.example.helloworld.login.LoginActivity;
import com.example.helloworld.login.SellerRegisterActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void navigateToSellerRegister(View view) {
        Intent in = new Intent(MainActivity.this, SellerRegisterActivity.class);
        startActivity(in);
    }

    public void navigateToClientRegister(View view) {
        Intent in = new Intent(MainActivity.this,ClientRegisterActivity.class);
        startActivity(in);
    }

    public void navigateToLogin(View view) {
        Intent in = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(in);
    }
}
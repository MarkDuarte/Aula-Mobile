package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","Inicia a Aplicação e mostra a Tela Inicial");
    }

    public void toRegisterClient(View view) {
        Intent intent = new Intent(this, DisplayRegisterClientActivity.class);
        startActivity(intent);
    }
    public void toRegisterSeller(View view) {
        Intent intent = new Intent(this, DisplayRegisterSellerActivity.class);
        startActivity(intent);
    }
}
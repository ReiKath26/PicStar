package com.example.picstar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class promo_premium extends AppCompatActivity {

    Button suscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_premium);
        suscribe = findViewById(R.id.btn_subscribe);

        suscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(promo_premium.this, subscribe.class);
                startActivity(intent);
            }
        });
    }
}
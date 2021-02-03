package com.example.almacen.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.almacen.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        Button historial_btn = findViewById(R.id.historial_select_btn);
        Button articles_btn = findViewById(R.id.articles_select_btn);

        historial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moviments_view = new Intent(HomeActivity.this, HistorialMoviments.class);
                startActivity(moviments_view);
            }
        });

        articles_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articles_view = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(articles_view);
            }
        });
    }
}
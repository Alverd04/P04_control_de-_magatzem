package com.example.almacen.Components;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.almacen.R;
import com.example.almacen.Services.MagatzemArticlesDatasource;

public class ArticleLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        MagatzemArticlesDatasource magatzemDatasource = new MagatzemArticlesDatasource(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_layout);


    }
}
package com.example.almacen.Views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.almacen.R;
import com.example.almacen.Services.MagatzemArticlesDatasource;
import com.example.almacen.Services.adapterArticle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private adapterArticle adapterArticle;
    private static MagatzemArticlesDatasource magatzemDatasource;

    private static String[] from = new String[]{
            magatzemDatasource.codi_article,
            magatzemDatasource.descripcio_article,
            magatzemDatasource.estoc_article,
            magatzemDatasource.preu_article,
            magatzemDatasource.preu_article};

    private static int[] to = new int[]{
            R.id.codi,
            R.id.descripcio,
            R.id.estoc_actual,
            R.id.preu_sense_iva,
            R.id.preu_iva};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        magatzemDatasource = new MagatzemArticlesDatasource(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showArticles();

        FloatingActionButton crear_btn = (FloatingActionButton) findViewById(R.id.crear_btn);

        crear_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FloatingActionButton add_stock_btn = findViewById(R.id.add_btn);
                add_stock_btn.setVisibility(View.VISIBLE);
                add_stock_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent add_stock_view = new Intent(MainActivity.this, AddStock.class);
                        startActivityForResult(add_stock_view, 1);
                    }
                });

                FloatingActionButton remove_stock_btn = findViewById(R.id.remove_btn);
                remove_stock_btn.setVisibility(View.VISIBLE);
                remove_stock_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent retrieve_stock_activity = new Intent(MainActivity.this, RetrieveStock.class);
                        startActivityForResult(retrieve_stock_activity, 1);
                    }
                });
                // treure add_stock_btn.setVisibility(View.GONE);

                return true;
            }
        });

        crear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createActivity = new Intent(MainActivity.this, CreateArticle.class);
                startActivityForResult(createActivity, 1);
            }
        });

        ListView lista = findViewById(R.id.articles);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editItemActivity = new Intent(MainActivity.this, CreateArticle.class);
                editItemActivity.putExtra("id", id);
                startActivityForResult(editItemActivity, 1);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // Set a title for alert dialog
                builder.setTitle("Select your answer.");

                // Ask the final question
                builder.setMessage("Estas segur que vols borrar l'Article?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        magatzemDatasource.deleteArticle(id);
                        showArticles();
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.descripcio_filtre_btn:
                showBusqueda();
                return true;
            case R.id.descripcio_estoc_btn:
                showArticlesDescripcioIEstoc();
                return true;
            case R.id.default_btn:
                showArticles();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showArticles(){
        Cursor articles = (Cursor) magatzemDatasource.articlesMagatzem();
        adapterArticle = new adapterArticle(this, R.layout.activity_article_layout, articles, from, to, 1);
        ListView lista = findViewById(R.id.articles);
        lista.setAdapter(adapterArticle);
    }

    public void showArticlesDescripcio(Cursor articles){
        adapterArticle = new adapterArticle(this, R.layout.activity_article_layout, articles, from, to, 1);
        ListView lista = findViewById(R.id.articles);
        lista.setAdapter(adapterArticle);
    }

    public void showArticlesDescripcioIEstoc(){
        Cursor articles = (Cursor) magatzemDatasource.articlesAmbDescripcioIEstocZeroOInferior();
        adapterArticle = new adapterArticle(this, R.layout.activity_article_layout, articles, from, to, 1);
        ListView lista = findViewById(R.id.articles);
        lista.setAdapter(adapterArticle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1){
            showArticles();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showBusqueda(){
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Buscador");

        EditText edtDescription = new EditText(this);
        alert.setView(edtDescription);

        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Buscar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String description = "";
                try {
                    description = edtDescription.getText().toString().toLowerCase();
                }
                catch (Exception e) {
                    description = "";
                }

                Cursor articles = magatzemDatasource.articlesAmbDescripcio(description);
                showArticlesDescripcio(articles);
            }
        });

        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alert.show();

    }

}


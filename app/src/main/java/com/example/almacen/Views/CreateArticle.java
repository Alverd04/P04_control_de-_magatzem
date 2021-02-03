package com.example.almacen.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almacen.Model.Article;
import com.example.almacen.R;
import com.example.almacen.Services.MagatzemArticlesDatasource;

public class CreateArticle extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MagatzemArticlesDatasource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_article);
                datasource = new MagatzemArticlesDatasource(this);
        EditText codi_article_edt = findViewById(R.id.codi_edt);
        EditText descripcio_article_edt = findViewById(R.id.descripcio_edt);
        Spinner familia_article_spinner = findViewById(R.id.familia_edt);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.familia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        familia_article_spinner.setAdapter(adapter);
        familia_article_spinner.setOnItemSelectedListener(this);
        EditText preu_article_edt = findViewById(R.id.preu_edt);
        EditText estoc_article_edt = findViewById(R.id.estoc_edt);
        TextView estoc_article_txt = findViewById(R.id.estoc_txt);
         Button cancel_btn = (Button) findViewById(R.id.cancel_btn);

         cancel_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);
        if(id >= 0){
            Cursor article = datasource.getArticle(id);
            if (article.moveToFirst()) {

                String code = article.getString(article.getColumnIndexOrThrow(datasource.codi_article));
                String descripcio = article.getString(article.getColumnIndexOrThrow(datasource.descripcio_article));
                String familia = article.getString(article.getColumnIndexOrThrow(datasource.familia_article));
                String preu = String.valueOf(article.getDouble(article.getColumnIndexOrThrow(datasource.preu_article)));
                String estoc = String.valueOf(article.getInt(article.getColumnIndexOrThrow(datasource.estoc_article)));

                switch(familia){
                    case "SOFTWARE":
                        familia_article_spinner.setSelection(0);
                        break;
                    case "HARDWARE":
                        familia_article_spinner.setSelection(1);
                        break;
                    case "ALTRES":
                        familia_article_spinner.setSelection(2);
                        break;
                    default:
                        familia_article_spinner.setSelection(3);
                        break;
                }

                codi_article_edt.setText(code);
                descripcio_article_edt.setText(descripcio);
                preu_article_edt.setText(preu);
                estoc_article_edt.setText(estoc);
                estoc_article_edt.setVisibility(View.GONE);
                estoc_article_txt.setVisibility(View.GONE);
            }
            else {

            }
        }else{

        }

        Button submit_btn = findViewById(R.id.submit_button);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preu_article_edt.getText().toString().length() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Siusplau, introdueix un preu", Toast.LENGTH_LONG);
                    toast.show();
                }else if(estoc_article_edt.getText().toString().length() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(), "Siusplau introdueix un estoc", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    String codi_article = codi_article_edt.getText().toString();
                    String descripcio_article = descripcio_article_edt.getText().toString();
                    String familia_article = familia_article_spinner.getSelectedItem().toString();
                    float preu_article = Float.valueOf(preu_article_edt.getText().toString());
                    int estoc_article = Integer.parseInt(estoc_article_edt.getText().toString());
                    if(id > 0){
                        actualitzarArticle(id, codi_article, descripcio_article, familia_article, preu_article, estoc_article);
                        finish();
                    }else{
                        crearArticle(codi_article, descripcio_article, familia_article, preu_article, estoc_article);
                    }
                }
            }
        });

    }

    private void crearArticle(String codi_article, String descripcio_article, String familia_article, float preu_article, int estoc_article){
        if(estoc_article < 0){
            Toast toast = Toast.makeText(getApplicationContext(), "L'estoc no pot ser inferior a 0", Toast.LENGTH_LONG);
            toast.show();
        }else if(descripcio_article.length() == 0){
            Toast toast = Toast.makeText(getApplicationContext(), "La descripció és obligatoria", Toast.LENGTH_LONG);
            toast.show();
        }else if(checkCodeExists(codi_article)==true){
            Toast toast = Toast.makeText(getApplicationContext(), "El codi d'article indicat ja existeix", Toast.LENGTH_LONG);
            toast.show();
        }else{
            Article nou_article = new Article(codi_article, descripcio_article, familia_article, preu_article, estoc_article);
            datasource.putArticle(nou_article);
            finish();
        }
    }

    private void actualitzarArticle(long id, String codi_article, String descripcio_article, String familia_article, float preu_article, int estoc_article){
            Article nou_article = new Article(codi_article, descripcio_article, familia_article, preu_article, estoc_article);
            datasource.updateArticle(id, nou_article);
            finish();
    }

    private boolean checkCodeExists(String codi_article){
        Cursor article = datasource.getArticleByCode(codi_article);
        if(article.moveToFirst()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

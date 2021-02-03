package com.example.almacen.Views;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.almacen.Model.Moviment;
import com.example.almacen.R;
import com.example.almacen.Services.MagatzemArticlesDatasource;
import com.example.almacen.Services.MagatzemMovimentsDatasource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class RetrieveStock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_stock);

        EditText retrieve_id = findViewById(R.id.retrieve_id);
        EditText retrieve_number = findViewById(R.id.retrieve_number);
        Button retrieve_cancel = findViewById(R.id.retrieve_cancel);
        Button retrieve_submit = findViewById(R.id.retrieve_submit);
        MagatzemMovimentsDatasource magatzemMovimentsDatasource = new MagatzemMovimentsDatasource(this);
        MagatzemArticlesDatasource magatzemArticlesDatasource = new MagatzemArticlesDatasource(this);

        DatePicker calendari = findViewById(R.id.date_picker_retrieve);

        retrieve_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String codiArticle = retrieve_id.getText().toString();
                int quantitat = Integer.parseInt("-" + retrieve_number.getText().toString());
                if(magatzemArticlesDatasource.getArticleByCode(codiArticle).moveToFirst()){
                    Date date;
                    int day = calendari.getDayOfMonth();
                    int month = calendari.getMonth();
                    int year =  calendari.getYear();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    date = calendar.getTime();

                    Moviment m = new Moviment(codiArticle, date, quantitat, 'S');
                    magatzemMovimentsDatasource.crearMoviment(m);
                    magatzemArticlesDatasource.actualitzarEstocArticle(codiArticle, quantitat);
                    finish();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "L'article seleccionat no existeix", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });

        retrieve_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

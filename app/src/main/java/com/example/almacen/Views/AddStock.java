package com.example.almacen.Views;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almacen.Model.Moviment;
import com.example.almacen.R;
import com.example.almacen.Services.MagatzemArticlesDatasource;
import com.example.almacen.Services.MagatzemMovimentsDatasource;

import java.util.Calendar;
import java.util.Date;

public class AddStock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        MagatzemArticlesDatasource magatzemArticlesDatasource = new MagatzemArticlesDatasource(this);
        MagatzemMovimentsDatasource magatzemMovimentsDatasource = new MagatzemMovimentsDatasource(this);

        EditText add_id = findViewById(R.id.add_id);
        EditText add_number = findViewById(R.id.add_number);
        Button add_cancel = findViewById(R.id.add_cancel);
        Button add_submit = findViewById(R.id.add_submit);

        DatePicker calendari = findViewById(R.id.date_picker_add);

        add_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String codiArticle = add_id.getText().toString();
                int quantitat = Integer.parseInt(add_number.getText().toString());
                if(magatzemArticlesDatasource.getArticleByCode(codiArticle).moveToFirst()){
                    Date date;
                    int day = calendari.getDayOfMonth();
                    int month = calendari.getMonth();
                    int year =  calendari.getYear();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    date = calendar.getTime();



                    Moviment m = new Moviment(codiArticle, date, quantitat, 'E');
                    magatzemMovimentsDatasource.crearMoviment(m);
                    magatzemArticlesDatasource.actualitzarEstocArticle(codiArticle, quantitat);
                    DatePicker date_picker = findViewById(R.id.date_picker_add);
                    finish();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "L'article seleccionat no existeix", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });


        add_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
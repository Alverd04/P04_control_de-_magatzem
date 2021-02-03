package com.example.almacen.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.almacen.Model.Moviment;
import com.example.almacen.R;
import com.example.almacen.Services.MagatzemMovimentsDatasource;
import com.example.almacen.Services.adapterArticle;
import com.example.almacen.Services.adapterMagatzem;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistorialMoviments extends AppCompatActivity {

    private MagatzemMovimentsDatasource magatzemMovimentsDatasource;
    private adapterMagatzem adapterMagatzem;

    private static String[] from = new String[]{
            MagatzemMovimentsDatasource.codi_article,
            MagatzemMovimentsDatasource.estoc_article,
            MagatzemMovimentsDatasource.dia_moviment
    };

    private static int[] to = new int[]{
            R.id.codi_moviment,
            R.id.estoc_actual_moviment,
            R.id.data_moviment
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        magatzemMovimentsDatasource = new MagatzemMovimentsDatasource(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_moviments);
        showMoviments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumagatzem, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.moviments_per_dia_select:
                showMovimentsPerDia();
                return true;
            case R.id.intervals_de_temps_select:
                showMovimentsPerIntervalDeTemps();
                return true;
            case R.id.llistat_complet_select:
                showMoviments();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showMovimentsPerDia(){
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker<Long> materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Long variable = selection;
                Date date_new = new Date(variable);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateFormatted = formatter.format(date_new);

                Cursor moviments_del_dia = magatzemMovimentsDatasource.getMovimentsByDia(dateFormatted);
                showMovimentsAmbCursor(moviments_del_dia);
            }
        });
    }

    public void showMovimentsAmbCursor(Cursor moviments){
        adapterMagatzem = new adapterMagatzem(this, R.layout.activity_historial_moviments_raw, moviments, from, to, 1);
        ListView lista = findViewById(R.id.historial_moviments);
        lista.setAdapter(adapterMagatzem);

    }

    public void showMovimentsPerIntervalDeTemps(){
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Selecciona una data");
        MaterialDatePicker materialDatePicker = builder.build();
        materialDatePicker.show(getSupportFragmentManager(), "DATE PICKER");
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                System.out.println(selection);
                Pair <Long, Long> pair = (Pair<Long, Long>) selection;
                Date date_inici = new Date(pair.first);
                Date date_final = new Date(pair.second);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String data_inici = formatter.format(date_inici);
                String data_final = formatter.format(date_final);

                Cursor movimentPeriodeTemps = magatzemMovimentsDatasource.getMovimentsByInterval(data_inici, data_final);
                System.out.println(movimentPeriodeTemps);
                showMovimentsAmbCursor(movimentPeriodeTemps);

            }
        });

    }

    public void showMoviments(){
        Cursor moviments = (Cursor) magatzemMovimentsDatasource.articlesMagatzem();
        showMovimentsAmbCursor(moviments);

    }

    public void showMovimentbyCodi(String codi){
        Cursor moviments = (Cursor) magatzemMovimentsDatasource.getMovimentsByCodi(codi);
        showMovimentsAmbCursor(moviments);
    }
}
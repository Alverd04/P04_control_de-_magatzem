package com.example.almacen.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.almacen.Model.Article;
import com.example.almacen.Model.Moviment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MagatzemMovimentsDatasource {

    public static final String table_magatzem = "magatzemMoviments";
    public static final String id_moviment = "_id";
    public static final String codi_article = "codiArticle";
    public static final String dia_moviment = "dia";
    public static final String estoc_article = "quantitat";
    public static final String tipus_moviment = "tipus";

    private SqLiteHelper dbHelper;
    private SQLiteDatabase dbW, dbR;

    public MagatzemMovimentsDatasource(Context context){
        // En el constructor directament obro la comunicació amb la base de dades
        dbHelper = new SqLiteHelper(context);

        // a més també construeixo dos databases un per llegir i l'altre per alterar
        open();
    }

    // DESTRUCTOR
    protected void finalize () {
        // Cerramos los databases
        dbW.close();
        dbR.close();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }

    /***Mètode per a retornar tots els articles del magatzem***/
    public Cursor articlesMagatzem(){
        return dbR.query(table_magatzem, new String[]{id_moviment,codi_article,dia_moviment,estoc_article,tipus_moviment},
                null, null,
                null, null, id_moviment);
    }

    /**Mètode per a obtenir tots els elements a partir de un codi**/

    public Cursor getMovimentsByCodi(String search_code){
        return dbR.query(table_magatzem, new String[]{id_moviment,codi_article,dia_moviment,estoc_article,tipus_moviment},
                codi_article + " = " + '"' + search_code + '"', null,
                null, null, id_moviment);
    }

    public Cursor getMovimentsByDia(String date){
        return dbR.query(table_magatzem, new String[]{id_moviment,codi_article,dia_moviment,estoc_article,tipus_moviment},
                dia_moviment + " = " + '"' + date + '"', null,
                null, null, id_moviment);
    }

    public Cursor getMovimentsByInterval(String data_inici, String data_final){
        return dbR.query(table_magatzem, new String[]{id_moviment,codi_article,dia_moviment,estoc_article,tipus_moviment},
                dia_moviment + " BETWEEN " + '"' + data_inici + '"' + " AND " + '"' + data_final + '"', null,
                null, null, id_moviment);
    }

    /***Mètode per a crear un moviment***/
    public void crearMoviment(Moviment moviment){
        // Creem una nova tasca i retornem el id crear per si el necessiten
        ContentValues values = new ContentValues();
        values.put(codi_article, moviment.getCodiArticle());
        Date date = moviment.getData();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormatted = formatter.format(date);

        values.put(dia_moviment, dateFormatted/*moviment.getData().toString()*/);
        values.put(estoc_article, moviment.getQuantitat());
        values.put(tipus_moviment, String.valueOf(moviment.getTipus()));
        dbW.insert(table_magatzem,null, values);

    }
}

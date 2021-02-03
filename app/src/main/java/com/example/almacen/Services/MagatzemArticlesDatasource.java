package com.example.almacen.Services;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.almacen.Model.Article;

public class MagatzemArticlesDatasource {

    public static final String table_magatzem = "magatzemArticles";
    public static final String id_article = "_id";
    public static final String codi_article = "codiArticle";
    public static final String descripcio_article = "descripcio";
    public static final String familia_article = "familia";
    public static final String preu_article = "preu";
    public static final String estoc_article = "estoc";

    private SqLiteHelper dbHelper;
    private SQLiteDatabase dbW, dbR;

    public MagatzemArticlesDatasource(Context context){
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
        return dbR.query(table_magatzem, new String[]{id_article,codi_article,descripcio_article,familia_article,preu_article, estoc_article},
                null, null,
                null, null, id_article);
    }

    /***Mètode per a retornar tots els articles amb descripció***/
    public Cursor articlesAmbDescripcio(String descripcio){
        return dbR.query(table_magatzem, new String[]{id_article,codi_article,descripcio_article,familia_article,preu_article, estoc_article},
                descripcio_article + " LIKE " + '"' + "%" + descripcio + "%" + '"' , null,
                null, null, id_article);
    }

    /***Mètode per a retornar tots els articles amb descripció i stock igual o inferior a zero ***/
    public Cursor articlesAmbDescripcioIEstocZeroOInferior(){
        return dbR.query(table_magatzem, new String[]{id_article,codi_article,descripcio_article,familia_article,preu_article, estoc_article},
                descripcio_article + " IS NOT NULL AND " + estoc_article + " <= 0" , null,
                null, null, id_article);
    }


    /***Mètode per a retornar un article el id del qual s'igui el passat per paràmetre***/
    public Cursor getArticle(long id){
        // Retorna un cursor només amb el id indicat
        // Retornem les tasques que el camp DONE = 1
        return dbR.query(table_magatzem, new String[]{id_article,codi_article,descripcio_article,familia_article,preu_article, estoc_article},
                id_article + " = " + id, null,
                null, null, null);
    }

    /**Mètode per a obtenir un Article a partir del seu codi**/
    public Cursor getArticleByCode(String article_code){
        return dbR.query(table_magatzem, new String[]{id_article,codi_article,descripcio_article,familia_article,preu_article, estoc_article},
                codi_article + " = " + '"' + article_code + '"', null,
                null, null, null);
    }

    /***Mètode per a crear un article***/
    public long putArticle(Article article){

        // Creem una nova tasca i retornem el id crear per si el necessiten
        ContentValues values = new ContentValues();

        values.put(codi_article, article.getClau_primaria());
        values.put(descripcio_article, article.getDescripcio());
        values.put(familia_article, article.getFamilia());
        values.put(preu_article, article.getPreu());
        values.put(estoc_article, article.getEstoc());

        return dbW.insert(table_magatzem,null, values);
    }

    /***Mètode per a actualitzar l'item***/
    public void updateArticle(long _id, Article article){

        ContentValues values = new ContentValues();

        values.put(codi_article, article.getClau_primaria());
        values.put(descripcio_article, article.getDescripcio());
        values.put(familia_article, article.getFamilia());
        values.put(preu_article, article.getPreu());
        values.put(estoc_article, article.getEstoc());

        dbW.update(table_magatzem,values, id_article + " = " + _id, null);
    }

    public void actualitzarEstocArticle(String codi_article, int moviment){
        Cursor cursor = this.getArticleByCode(codi_article);
        if(cursor.moveToFirst()){

            String id = cursor.getString(cursor.getColumnIndex(id_article));
            String descripcio = cursor.getString(cursor.getColumnIndex(descripcio_article));
            String familia = cursor.getString(cursor.getColumnIndex(familia_article));
            Float preu = cursor.getFloat(cursor.getColumnIndex(preu_article));
            int estoc = cursor.getInt(cursor.getColumnIndex(estoc_article)) + moviment;

            Article article = new Article(id, descripcio, familia, preu, estoc);
            updateArticle(Long.parseLong(id), article);
        }
    }

    /**Mètode per a borrar l'article */
    public void deleteArticle(long _id){
        dbW.delete(table_magatzem, id_article + " = " + _id, null);
    }
}
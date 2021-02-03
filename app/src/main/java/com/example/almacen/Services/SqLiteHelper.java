package com.example.almacen.Services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqLiteHelper extends SQLiteOpenHelper {

    // database version
    private static final int database_VERSION = 2;

    // database name
    private static final String database_NAME = "magatzemDatabase";

    public SqLiteHelper(Context context){
        super(context, database_NAME, null, database_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLE =
                "CREATE TABLE magatzemArticles ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codiArticle TEXT," +
                        "descripcio TEXT," +
                        "familia TEXT," +
                        "preu REAL," +
                        "estoc REAL)";

        String CREATE_HISTORIC =
                "CREATE TABLE magatzemMoviments ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "codiArticle TEXT," +
                        "dia TEXT," +
                        "quantitat REAL," +
                        "tipus TEXT)";

        db.execSQL(CREATE_ARTICLE);
        db.execSQL(CREATE_HISTORIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            String CREATE_HISTORIC =
                    "CREATE TABLE magatzemMoviments ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "codiArticle TEXT," +
                            "dia TEXT," +
                            "quantitat REAL," +
                            "tipus TEXT)";
            db.execSQL(CREATE_HISTORIC);
        }
    }
}

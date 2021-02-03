package com.example.almacen.Services;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.almacen.R;

import org.w3c.dom.Text;

public class adapterArticle extends android.widget.SimpleCursorAdapter{
    public adapterArticle(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        // Agafem l'objecte de la view que es una LINEA DEL CURSOR
        Cursor linia = (Cursor) getItem(position);

        TextView precio = (TextView) view.findViewById(R.id.preu_iva);
        double precio_articulo = round(linia.getDouble(linia.getColumnIndexOrThrow(MagatzemArticlesDatasource.preu_article))*1.21, 2);
        precio.setText(String.valueOf(precio_articulo));
        TextView estoc = (TextView) view.findViewById(R.id.estoc_actual);

        int estoc_article = linia.getInt(linia.getColumnIndexOrThrow(MagatzemArticlesDatasource.estoc_article));
        if(estoc_article <= 0){
            estoc.setBackgroundColor(Color.parseColor("#ff6f61"));
        }
        else{
            estoc.setBackground(null);
        }
        if(position%2 == 0){
            view.setBackgroundColor(Color.parseColor("#61a2ff"));
        }else{
            view.setBackgroundColor(Color.parseColor("#ffbe61"));
        }
        return view;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}

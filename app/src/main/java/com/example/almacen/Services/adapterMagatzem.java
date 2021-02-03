package com.example.almacen.Services;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.almacen.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class adapterMagatzem extends android.widget.SimpleCursorAdapter{

    public adapterMagatzem(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        // Agafem l'objecte de la view que es una LINEA DEL CURSOR
        Cursor linia = (Cursor) getItem(position);

        String tipus = linia.getString(linia.getColumnIndexOrThrow("tipus"));
        if(tipus.equals("E")){
            view.setBackgroundColor(Color.parseColor("#77dd77"));
        }else if(tipus.equals("S")){
            view.setBackgroundColor(Color.parseColor("#ff6961"));
        }else{
            view.setBackgroundColor(Color.WHITE);
        }
        return view;
    }

}

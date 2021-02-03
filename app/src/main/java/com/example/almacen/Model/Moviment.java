package com.example.almacen.Model;

import java.util.Date;

public class Moviment {

    private String codiArticle;
    private Date data;
    private int quantitat;
    private char tipus;

    public Moviment(String codiArticle, Date data, int quantitat, char tipus) {
        this.codiArticle = codiArticle;
        this.data = data;
        this.quantitat = quantitat;
        this.tipus = tipus;
    }

    public String getCodiArticle() {
        return codiArticle;
    }

    public void setCodiArticle(String codiArticle) {
        this.codiArticle = codiArticle;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public char getTipus() {
        return tipus;
    }

    public void setTipus(char tipus) {
        this.tipus = tipus;
    }
}

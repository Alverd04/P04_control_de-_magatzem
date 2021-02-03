package com.example.almacen.Model;

public class Article {

    private String clau_primaria;
    private String descripcio;
    private String familia; // SOFTWARE, HARDWARE i ALTRES
    private float preu;
    private int estoc;

    public Article(String clau_primaria, String descripcio, String familia, float preu, int estoc) {
        this.clau_primaria = clau_primaria;
        this.descripcio = descripcio;
        this.familia = familia;
        this.preu = preu;
        this.estoc = estoc;
    }

    public String getClau_primaria() {
        return clau_primaria;
    }

    public void setClau_primaria(String clau_primaria) {
        this.clau_primaria = clau_primaria;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getEstoc() {
        return estoc;
    }

    public void setEstoc(int estoc) {
        this.estoc = estoc;
    }
}

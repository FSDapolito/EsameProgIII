package com.example.progettoprogiii;
public class Prodotto {
    private String codice;
    private String nome;
    private int scorta;
    private double costo;
    private double sconto;
    private int categoria_id;
    private int fornitore_id;

    public void setCodice(String codice){this.codice=codice;}
    public void setNome(String nome){this.nome=nome;}

    public void setScorta(int scorta){this.scorta=scorta;}
    public void setCosto(double costo){this.costo=costo;}
    public void setSconto(double sconto){this.sconto=sconto;}

    public void setCategoriaId(int categoria_id){this.categoria_id=categoria_id;}
    public void setFornitoreId(int fornitore_id){this.fornitore_id=fornitore_id;}
    private double prezzo;

    public double getPrezzo() {
        return prezzo;
    }






    public String getNome(){return this.nome;}
    public int getScorta(){return this.scorta;}
    public Double getCosto(){return this.costo;}
}
package com.example.progettoprogiii;

public interface PreventivoBuilder {
    void buildFornitore(String fornitore);
    void buildPezzo(String pezzo);
    void buildQuantita(int quantita);
    Preventivo getPreventivo();
}
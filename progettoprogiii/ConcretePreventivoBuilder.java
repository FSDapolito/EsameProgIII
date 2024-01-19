package com.example.progettoprogiii;

public class ConcretePreventivoBuilder implements PreventivoBuilder {
    private Preventivo preventivo;

    public ConcretePreventivoBuilder() {
        this.preventivo = new Preventivo();
    }

    @Override
    public void buildFornitore(String fornitore) {
        preventivo.setFornitore(fornitore);
    }

    @Override
    public void buildPezzo(String pezzo) {
        preventivo.setPezzo(pezzo);
    }

    @Override
    public void buildQuantita(int quantita) {
        preventivo.setQuantita(quantita);
    }

    @Override
    public Preventivo getPreventivo() {
        return preventivo;
    }
}
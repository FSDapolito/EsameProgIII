package com.example.progettoprogiii;

public class RemoveProdottoCarr implements Command {
    private Catalogo catalogo;
    private Integer quantita;
    private Integer i;

    public RemoveProdottoCarr(Catalogo catalogo, Integer quantita,Integer i) {
        this.catalogo = catalogo;
        this.quantita = quantita;
        this.i=i;
    }

    @Override
    public void execute(Integer quantita,Integer i) {
        catalogo.rimuoviProdotto(quantita,i);
    }
}
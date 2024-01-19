package com.example.progettoprogiii;
//CARRELLO
public interface StatoCarrello {
    void aggiungiProdotto(Prodotto prodotto, Carrello carrello);
    void rimuoviProdotto(Prodotto prodotto, Carrello carrello);
    void effettuaPagamento(Carrello carrello);


}

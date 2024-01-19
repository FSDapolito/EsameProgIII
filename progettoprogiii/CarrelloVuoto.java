package com.example.progettoprogiii;

class CarrelloVuoto implements StatoCarrello {
    @Override
    public void aggiungiProdotto(Prodotto prodotto, Carrello carrello) {
        carrello.getProdotti().add(prodotto);
        System.out.println("Prodotto aggiunto al carrello.");
        carrello.setStato(new CarrelloPieno());
    }

    @Override
    public void rimuoviProdotto(Prodotto prodotto, Carrello carrello) {
        System.out.println("Impossibile rimuovere. Il carrello è vuoto.");
    }

    @Override
    public void effettuaPagamento(Carrello carrello) {
        System.out.println("Impossibile effettuare il pagamento. Il carrello è vuoto.");
    }
}

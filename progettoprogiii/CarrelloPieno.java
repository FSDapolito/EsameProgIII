package com.example.progettoprogiii;

class CarrelloPieno implements StatoCarrello {
    @Override
    public void aggiungiProdotto(Prodotto prodotto, Carrello carrello) {
        System.out.println("Impossibile aggiungere. Il carrello è pieno.");
    }

    @Override
    public void rimuoviProdotto(Prodotto prodotto, Carrello carrello) {
        carrello.getProdotti().remove(prodotto);
        System.out.println("Prodotto rimosso dal carrello.");
        if (carrello.getProdotti().isEmpty()) {
         //   carrello.setStato(new CarrelloVuoto());
        }
    }

    @Override
    public void effettuaPagamento(Carrello carrello) {
        System.out.println("Pagamento effettuato. Grazie!");
       // carrello.svuotaCarrello();
        //carrello.setStato(new CarrelloVuoto());
    }
}

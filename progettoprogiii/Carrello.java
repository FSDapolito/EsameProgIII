package com.example.progettoprogiii;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Carrello {


    public Carrello() {
        this.stato = new CarrelloVuoto();
        this.prodotti = new ArrayList<>();
    }

    private StatoCarrello stato;
    private List<Prodotto> prodotti;
    private Integer quantita;

    public void setQuantita(Integer q){
        quantita=q;
    }
    public Integer getQuantita(){
        return quantita;
    }

    public boolean isVuoto() {
        return prodotti == null || prodotti.isEmpty();
    }
    int q=0;
    public void aggiungiProdotto(Prodotto prodotto) {

        q=q+prodotto.getScorta();

        if (q>10) {

            stato.aggiungiProdotto(prodotto, this);
        }else prodotti.add(prodotto);
    }

    public void rimuoviProdotto(Prodotto prodotto, int quantita) {
        Iterator<Prodotto> iterator = prodotti.iterator();

        while (iterator.hasNext()) {
            Prodotto p = iterator.next();

            if (p.equals(prodotto)) {
                int s = p.getScorta() - quantita;
                if (s <= 0) {
                    iterator.remove();  // Rimuovi l'elemento utilizzando l'iteratore
                } else {
                    p.setScorta(s);
                }
                break;  // Esci dal ciclo, abbiamo trovato e gestito il prodotto
            }
        }


    }
    public void setStato(CarrelloPieno stato) {
        this.stato = stato;
    }



    public List<Prodotto> getProdotti() {
        return prodotti;
    }

    private MetodoPagamento metodoPagamento;

    public void impostaMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void effettuaPagamento() {
        if (metodoPagamento != null) {
            double importoTotale = calcolaImportoTotale();
            // Utilizza il metodo elabarazionePagamento della classe MetodoPagamento
            metodoPagamento.elaboraPagamento(importoTotale);
            // Aggiungi eventuali logiche aggiuntive legate al pagamento
        } else {
            System.out.println("Errore: Metodo di pagamento non impostato.");
        }
    }
    private double calcolaImportoTotale() {
        double importoTotale = 0.0;
        for (Prodotto prodotto : prodotti) {
            importoTotale += prodotto.getPrezzo() * prodotto.getScorta();
        }
        return importoTotale;
    }
}
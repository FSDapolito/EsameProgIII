package com.example.progettoprogiii;

class BonificoBancario implements MetodoPagamento {
    @Override
    public void elaboraPagamento(double importo) {
        System.out.println("Pagamento effettuato tramite bonifico bancario");
    }
    @Override
    public String toString() {
        return "Bonifico Bancario";
    }
}

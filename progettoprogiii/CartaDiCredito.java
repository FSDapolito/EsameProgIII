package com.example.progettoprogiii;

class CartaDiCredito implements MetodoPagamento {
    @Override
    public void elaboraPagamento(double importo) {
        System.out.println("Pagamento effettuato con carta di credito");
    }
    @Override
    public String toString() {
        return "Carta di credito";
    }
}

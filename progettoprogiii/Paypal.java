package com.example.progettoprogiii;

class Paypal implements MetodoPagamento {
    @Override
    public void elaboraPagamento(double importo) {
        System.out.println("Pagamento effettuato tramite PayPal");
    }
    @Override
    public String toString() {
        return "Paypal";
    }
}
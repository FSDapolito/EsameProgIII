package com.example.progettoprogiii;


public class ProdottoFactory implements Factory{

    @Override
    public Prodotto createProdotto() {
        return new ProdottoSpecifico();
    }

    @Override
    public Fornitore createFornitore() {
        return new FornitoreSpecifico();
    }

    @Override
    public Categoria createCategoria() {
        return new CategoriaSpecifica();
    }
}

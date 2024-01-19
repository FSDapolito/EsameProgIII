package com.example.progettoprogiii;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {
    private final Connection connection;

    public ProdottoDAO() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Errore nella connessione al database", e);
        }
    }

    public List<Prodotto> getListaProdotti() {
        List<Prodotto> listaProdotti = new ArrayList<>();

        try {
            String query = "SELECT * FROM prodotti";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Prodotto prodotto = new Prodotto();
                    prodotto.setCodice(resultSet.getString("codice"));
                    prodotto.setNome(resultSet.getString("nome"));
                    prodotto.setScorta(resultSet.getInt("scorta"));
                    prodotto.setCosto(resultSet.getDouble("costo"));
                    prodotto.setSconto(resultSet.getDouble("sconto"));
                    prodotto.setCategoriaId(resultSet.getInt("categoria_id"));
                    prodotto.setFornitoreId(resultSet.getInt("fornitore_id"));

                    listaProdotti.add(prodotto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la lettura dei prodotti dal database", e);
        }

        return listaProdotti;
    }

    public List<Prodotto> getProdottiPerCategoria(String nomeCategoria) {
        List<Prodotto> listaProdotti = new ArrayList<>();

        try {
            String query = "SELECT p.* FROM prodotti p " +
                    "JOIN categorie c ON p.categoria_id = c.id " +
                    "WHERE c.nome = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nomeCategoria);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Prodotto prodotto = new Prodotto();
                        prodotto.setCodice(resultSet.getString("codice"));
                        prodotto.setNome(resultSet.getString("nome"));
                        prodotto.setScorta(resultSet.getInt("scorta"));
                        prodotto.setCosto(resultSet.getDouble("costo"));
                        prodotto.setSconto(resultSet.getDouble("sconto"));
                        prodotto.setCategoriaId(resultSet.getInt("categoria_id"));
                        prodotto.setFornitoreId(resultSet.getInt("fornitore_id"));

                        listaProdotti.add(prodotto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la lettura dei prodotti per categoria", e);
        }

        return listaProdotti;
    }

    public List<Prodotto> getProdottiPerFornitore(String nomeFornitore) {
        List<Prodotto> listaProdotti = new ArrayList<>();

        try {
            String query = "SELECT p.* FROM prodotti p " +
                    "JOIN fornitori f ON p.fornitore_id = f.id " +
                    "WHERE f.nome = ?";

            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, nomeFornitore);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Prodotto prodotto = new Prodotto();
                        prodotto.setCodice(resultSet.getString("codice"));
                        prodotto.setNome(resultSet.getString("nome"));
                        prodotto.setScorta(resultSet.getInt("scorta"));
                        prodotto.setCosto(resultSet.getDouble("costo"));
                        prodotto.setSconto(resultSet.getDouble("sconto"));
                        prodotto.setCategoriaId(resultSet.getInt("categoria_id"));
                        prodotto.setFornitoreId(resultSet.getInt("fornitore_id"));

                        listaProdotti.add(prodotto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante la lettura dei prodotti per fornitore", e);
        }

        return listaProdotti;
    }

}

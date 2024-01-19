package com.example.progettoprogiii;

import java.sql.*;

public class Preventivo {
    private String fornitore;
    private String pezzo;
    private int quantita;

    public Preventivo() {
        // Costruttore vuoto
    }

    public String getFornitore() {
        return fornitore;
    }

    public void setFornitore(String fornitore) {
        this.fornitore = fornitore;
    }

    public String getPezzo() {
        return pezzo;
    }

    public void setPezzo(String pezzo) {
        this.pezzo = pezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public double getPrezzo() {
        // Connessione al database e query per ottenere il prezzo del prodotto
        String URL = "jdbc:mysql://localhost:3306/ricambi";
        String USERNAME = "root";
        String PASSWORD = "Programmazione3";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT costo FROM prodotti WHERE fornitore_id IN (SELECT id FROM fornitori WHERE nome = ?) AND nome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, fornitore);
                preparedStatement.setString(2, pezzo);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        double costoUnitario = resultSet.getDouble("costo");
                        return quantita * costoUnitario;
                    } else {
                        System.out.println("Il pezzo specificato non esiste nel database.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la connessione al database.");
        }

        return 0.0; // Valore di default, potrebbe essere opportuno gestire questo caso in modo diverso
    }
}

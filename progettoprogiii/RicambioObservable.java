package com.example.progettoprogiii;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class RicambioObservable {
    private String nome;
    private List<Observer> observers = new ArrayList<>();

    public RicambioObservable(String nome){
        this.nome=nome;
    }

    //ADD
    public void addObserver(Observer observer){
        observers.add(observer);
    }

    //REMOVE
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }

    //UPDATE
    public void updateScortaFromDatabase(){
        int nuovaScorta = queryScortaFromDatabase();
        notifyObservers(nuovaScorta);
    }

    private int queryScortaFromDatabase() {
        int nuovaScorta = 0;

        // Configura la connessione al database
        String url = "jdbc:mysql://localhost:3306/ricambi";
        String username = "root";
        String password = "Programmazione3";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT scorta FROM prodotti WHERE nome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nome);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        nuovaScorta = resultSet.getInt("scorta");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante il recupero della scorta dal database.");
        }

        return nuovaScorta;
    }



//NOTIFY
    private void notifyObservers(int nuovaScorta){
        for(Observer observer : observers){
            observer.update(nome,nuovaScorta);
        }
    }

}

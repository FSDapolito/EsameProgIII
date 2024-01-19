package com.example.progettoprogiii;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewProduct {

    @FXML
    private TextField categoria;

    @FXML
    private TextField codice;

    @FXML
    private TextField costo;

    @FXML
    private TextField fornitore;

    @FXML
    private TextField nome;

    @FXML
    private TextField sconto;

    @FXML
    private TextField scorta;

    @FXML
    void addProductToDatabase(MouseEvent event) {

        String codiceValue = codice.getText();
        String nomeValue = nome.getText();
        int scortaValue = Integer.parseInt(scorta.getText());
        double costoValue = Double.parseDouble(costo.getText());
        double scontoValue = Double.parseDouble(sconto.getText());
        String categoriaValue = categoria.getText();
        String fornitoreValue = fornitore.getText();

        System.out.println(codiceValue);
        System.out.println(nomeValue);
        System.out.println(scortaValue);
        System.out.println(costoValue);
        System.out.println(scontoValue);
        System.out.println(categoriaValue);
        System.out.println(fornitoreValue);


        int categoriaId = ottieniCategoriaId(categoriaValue);
        if(categoriaId==-1)
        {System.out.println("La categoria inserita non esiste");}

        int fornitoreId = ottieniFornitoreId(fornitoreValue);
        if(categoriaId==-1)
        {System.out.println("Il fornitore inserito non esiste");}

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ricambi", "root", "Programmazione3")) {

            // Query SQL per l'inserimento dei dati
            String query = "INSERT INTO prodotti (codice, nome, scorta, costo, sconto, categoria_id, fornitore_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, codiceValue);
                preparedStatement.setString(2, nomeValue);
                preparedStatement.setInt(3, scortaValue);
                preparedStatement.setDouble(4, costoValue);
                preparedStatement.setDouble(5, scontoValue);
                preparedStatement.setInt(6, categoriaId);
                preparedStatement.setInt(7, fornitoreId);

                // Esegui l'inserimento
                preparedStatement.executeUpdate();

                System.out.println("Prodotto inserito con successo nel database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La Query non e' andata a buon fine");
        }
    }


        private int ottieniCategoriaId(String categoriaNome) {
                if(categoriaNome.equals("Aspirapolvere") || categoriaNome.equals("aspirapolvere")){ return 1;}
                else if(categoriaNome.equals("Frigorifero")|| categoriaNome.equals("frigorifero")){ return 2;}
                 else if (categoriaNome.equals("Lavatrice") ||categoriaNome.equals("lavatrice") ) {return 3;}
                else if (categoriaNome.equals("Lavastoviglie") ||categoriaNome.equals("lavastoviglie") ) {return 4;}
                return -1;
            }

        private int ottieniFornitoreId(String fornitoreNome) {
            if(fornitoreNome.equals("Bosch") || fornitoreNome.equals("bosh")){ return 1;}
            else if(fornitoreNome.equals("electrolux")|| fornitoreNome.equals("Electrolux")){ return 2;}
            else if (fornitoreNome.equals("Dyson") ||fornitoreNome.equals("dyson") ) {return 3;}
            else if (fornitoreNome.equals("lg") ||fornitoreNome.equals("Lg") ) {return 4;}
            return -1;
        }


    @FXML
    void backPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Parent BackRoot = (Parent) loader.load();
            Stage BackStage = new Stage();
            BackStage.setTitle("Admin Login");
            BackStage.setScene(new Scene(BackRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            BackStage.show();
        }
        catch (Exception e)
        {
            System.out.println("Errore di Login");
        }
    }

}

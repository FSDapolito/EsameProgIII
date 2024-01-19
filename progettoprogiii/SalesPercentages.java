package com.example.progettoprogiii;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class SalesPercentages {

    @FXML
    private Button aspirapolvereButton;

    @FXML
    private Button frigoButton;

    @FXML
    private ImageView immagine;

    @FXML
    private Button lavastoviglieButton;

    @FXML
    private Button lavatriceButton;

    @FXML
    private ImageView arrow;

    @FXML
    private BarChart<String, Integer> barChar;

    @FXML
    void infoAspirapolvere(MouseEvent event) {
        String nomeImmagine = "Lavastoviglie.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.immagine.setImage(image);
        this.caricaDatiCategoria("lavastoviglie");
    }

    @FXML
    void infoFrigorifero(MouseEvent event) {
        String nomeImmagine = "Frigo.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.immagine.setImage(image);
        this.caricaDatiCategoria("frigorifero");
    }

    @FXML
    void infoLavastoviglie(MouseEvent event) {
        String nomeImmagine = "Lavastoviglie.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.immagine.setImage(image);
        this.caricaDatiCategoria("lavastoviglie");
    }

    @FXML
    void infoLavatrice(MouseEvent event) {
        String nomeImmagine = "Lavatrice.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.immagine.setImage(image);
        this.caricaDatiCategoria("lavatrice");
    }

    @FXML
    void backFile(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Parent backRoot = (Parent) loader.load();
            Stage backStage = new Stage();
            backStage.setTitle("Admin Homepage");
            backStage.setScene(new Scene(backRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            backStage.show();
        } catch (Exception e) {
            System.out.println("Errore durante il recupero dei dati dal database.");
        }
    }





    private void caricaDatiCategoria(String categoria) {
        String url = "jdbc:mysql://localhost:3306/ricambi";
        String username = "root";
        String password = "Programmazione3";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT nome, scorta FROM prodotti WHERE categoria_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                int categoriaId = ottieniIdCategoria(categoria);
                preparedStatement.setInt(1, categoriaId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Pulisci i dati esistenti nel grafico
                    barChar.getData().clear();

                    // Crea una serie per il grafico
                    XYChart.Series<String, Integer> series = new XYChart.Series<>();

                    while (resultSet.next()) {
                        String nomeProdotto = resultSet.getString("nome");
                        int quantitaInScorta = resultSet.getInt("scorta");

                        // Aggiungi dati alla serie
                        series.getData().add(new XYChart.Data<>(nomeProdotto, quantitaInScorta));
                    }

                    // Aggiungi la serie al grafico
                    barChar.getData().add(series);

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante il recupero dei dati dal database.");
        }
    }


    private int ottieniIdCategoria(String categoriaNome) {
        if (categoriaNome.equalsIgnoreCase("Aspirapolvere")) {
            return 1;
        } else if (categoriaNome.equalsIgnoreCase("Frigorifero")) {
            return 2;
        } else if (categoriaNome.equalsIgnoreCase("Lavatrice")) {
            return 3;
        } else if (categoriaNome.equalsIgnoreCase("Lavastoviglie")) {
            return 4;
        }
        return -1;
    }
}

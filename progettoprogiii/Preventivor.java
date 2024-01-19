package com.example.progettoprogiii;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Preventivor {

    private PreventivoBuilder preventivoBuilder;
    private List<Preventivo> preventiviAggiunti; // Lista per memorizzare i preventivi aggiunti

    @FXML
    private ComboBox<String> fornitoreComboBox;
    @FXML
    private ComboBox<String> pezzoComboBox;
    @FXML
    private TextField quantitaTextField;
    @FXML
    private Button aggiungiPezzoButton; // Aggiunto il pulsante
    private static final String URL = "jdbc:mysql://localhost:3306/ricambi";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Programmazione3";
    private List<Preventivo> preventiviInseritiPrecedentemente; // Nuova lista per memorizzare preventivi inseriti precedentemente


    public Preventivor() {
        this.preventivoBuilder = new ConcretePreventivoBuilder();
        this.preventiviAggiunti = new ArrayList<>();
        this.preventiviInseritiPrecedentemente = new ArrayList<>(); // Inizializza la nuova lista
    }
    @FXML
    public void initialize() {
        // Popola la ComboBox dei fornitori
        populateComboBox(fornitoreComboBox, "fornitori");

        // Aggiungi un listener per gestire gli eventi di selezione sulla ComboBox dei fornitori
        fornitoreComboBox.setOnAction(event -> {
            // Quando un fornitore viene selezionato, carica i prodotti associati
            String selectedFornitore = fornitoreComboBox.getValue();
            if (selectedFornitore != null) {
                populateProdottiComboBox(selectedFornitore);
            }
        });
    }
    private void populateProdottiComboBox(String fornitore) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            // Utilizza una query parametrizzata per evitare SQL injection
            String query = "SELECT nome FROM prodotti WHERE fornitore_id IN (SELECT id FROM fornitori WHERE nome = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, fornitore);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<String> data = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        data.add(resultSet.getString("nome"));
                    }
                    pezzoComboBox.setItems(data);

                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Errore durante l'esecuzione della query.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la connessione al database.");
        }
    }

    @FXML
    private void aggiungiPezzoAlPreventivo(MouseEvent event) {
        String selectedFornitore = fornitoreComboBox.getValue();
        String selectedPezzo = pezzoComboBox.getValue();
        String quantitaText = quantitaTextField.getText();

        if (selectedFornitore != null && selectedPezzo != null && !quantitaText.isEmpty()) {
            try {
                int quantita = Integer.parseInt(quantitaText);

                // Usa il builder per aggiungere un pezzo al preventivo corrente
                preventivoBuilder.buildFornitore(selectedFornitore);
                preventivoBuilder.buildPezzo(selectedPezzo);
                preventivoBuilder.buildQuantita(quantita);

                // Ottieni l'oggetto Preventivo corrente
                Preventivo preventivoCorrente = preventivoBuilder.getPreventivo();

                // Aggiungi il preventivo alla lista
                preventiviAggiunti.add(preventivoCorrente);

                // Mostra un popup con le informazioni del pezzo aggiunto
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pezzo Aggiunto");
                alert.setHeaderText(null);
                alert.setContentText("Il pezzo è stato aggiunto al preventivo:\n" +
                        "Fornitore: " + preventivoCorrente.getFornitore() + "\n" +
                        "Pezzo: " + preventivoCorrente.getPezzo() + "\n" +
                        "Quantità: " + preventivoCorrente.getQuantita());
                alert.showAndWait();

                // Pulisci i campi per consentire l'aggiunta di nuovi pezzi
                fornitoreComboBox.getSelectionModel().clearSelection();
                pezzoComboBox.getItems().clear();
                quantitaTextField.clear();

                // Resettare il builder dopo aver costruito il preventivo
                this.preventivoBuilder = new ConcretePreventivoBuilder();
            } catch (NumberFormatException e) {
                System.out.println("Errore durante la conversione del testo in numero.");
            }
        } else {
            System.out.println("Seleziona fornitore, pezzo e inserisci la quantità.");
        }
    }


    private void populateComboBox(ComboBox<String> comboBox, String tableName) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT nome FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                ObservableList<String> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    data.add(resultSet.getString("nome"));
                }
                comboBox.setItems(data);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Errore durante l'esecuzione della query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la connessione al database.");
        }
    }

    @FXML
    private void calcolaPreventivo(MouseEvent event) {
        // Recupera i dati necessari dai preventivi aggiunti
        if (!preventiviAggiunti.isEmpty()) {
            try {
                double preventivoTotale = 0.0;

                // Calcola il preventivo totale considerando tutti gli oggetti nel preventivo e quelli immessi precedentemente
                for (Preventivo preventivo : preventiviAggiunti) {
                    double costoUnitario = ottieniCostoUnitario(preventivo.getFornitore(), preventivo.getPezzo());
                    preventivoTotale += preventivo.getQuantita() * costoUnitario;
                }

                // Mostra un popup con il preventivo totale
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Preventivo Totale");
                alert.setHeaderText(null);
                alert.setContentText("Il preventivo totale è: " + preventivoTotale + " euro");
                alert.showAndWait();

                // Pulisci i campi per consentire l'aggiunta di nuovi pezzi
                fornitoreComboBox.getSelectionModel().clearSelection();
                pezzoComboBox.getItems().clear();
                quantitaTextField.clear();

                // Resettare il builder dopo aver costruito il preventivo
                this.preventivoBuilder = new ConcretePreventivoBuilder();
            } catch (NumberFormatException e) {
                System.out.println("Errore durante la conversione del testo in numero.");
            }
        } else {
            System.out.println("Aggiungi almeno un pezzo al preventivo prima di calcolare il preventivo totale.");
        }
    }


    private double ottieniCostoUnitario(String fornitore, String pezzo) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String query = "SELECT costo FROM prodotti WHERE fornitore_id IN (SELECT id FROM fornitori WHERE nome = ?) AND nome = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, fornitore);
                preparedStatement.setString(2, pezzo);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getDouble("costo");
                    } else {
                        System.out.println("Il pezzo specificato non esiste nel database.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la connessione al database.");
        }
        return 0; // Valore di default, potrebbe essere opportuno gestire questo caso in modo diverso
    }

    @FXML
    private void backFile(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ClientHomePage.fxml"));
            Parent root2 = loader.load();
            Stage terzaStage = new Stage();
            terzaStage.setTitle("Client Homepage");
            terzaStage.setScene(new Scene(root2));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            terzaStage.show();
        } catch (Exception var6) {
            System.out.println("Errore di Login");
        }
    }


}

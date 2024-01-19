package com.example.progettoprogiii;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarrelloController {

    private Carrello cart;

    @FXML
    private Label totdapagare;

    @FXML
    private ListView<Integer> listqtcart;

    @FXML
    private ListView<String> listprod;

    @FXML
    private ChoiceBox<MetodoPagamento> choiceMetodoPagamento;

    public void setCarrello(Carrello cart) {
        this.cart = cart;
    }

    @FXML
    private void initialize() {
        // Inizializza la ChoiceBox dei metodi di pagamento con le opzioni desiderate
        ObservableList<MetodoPagamento> metodiPagamentoOptions = FXCollections.observableArrayList(
                new Paypal(),
                new BonificoBancario(),
                new CartaDiCredito()
        );
        choiceMetodoPagamento.setItems(metodiPagamentoOptions);
        choiceMetodoPagamento.setValue(metodiPagamentoOptions.get(0));
    }

    @FXML
    void stampaArticoli(MouseEvent event) {
        listprod.setItems(FXCollections.observableArrayList());
        listqtcart.setItems(FXCollections.observableArrayList());
        double tot = 0.0;
        List<Prodotto> prodottiInCart = cart.getProdotti();

        // Crea una lista di stringhe per rappresentare i prodotti come stringhe
        List<String> prodottiAsString = new ArrayList<>();
        List<Integer> QuantitaAsInt = new ArrayList<>();
        for (Prodotto x : prodottiInCart) {
            // Formatta il prodotto come una stringa (puoi personalizzarlo a seconda delle tue esigenze)
            String prodottoString = x.getNome() + " " + x.getCosto() + " €";
            int quantitaprodotto = x.getScorta();
            tot = tot + (quantitaprodotto * x.getCosto());
            prodottiAsString.add(prodottoString);
            QuantitaAsInt.add(quantitaprodotto);
        }

        // Aggiorna la ListView listaprod
        ObservableList<String> data = FXCollections.observableArrayList(prodottiAsString);
        ObservableList<Integer> data2 = FXCollections.observableArrayList(QuantitaAsInt);
        listprod.setItems(data);
        listqtcart.setItems(data2);
        totdapagare.setText(String.valueOf(tot));
    }

    @FXML
    void pagamento(MouseEvent event) {
        if (listprod.getItems().isEmpty()) {
            showAlert("Carrello Vuoto", "Il carrello è vuoto", "Non ci sono prodotti da pagare", Alert.AlertType.INFORMATION);
        } else {
            MetodoPagamento metodoPagamentoSelezionato = choiceMetodoPagamento.getValue();

            if (metodoPagamentoSelezionato == null) {
                showAlert("Errore", "Seleziona un metodo di pagamento", "Devi scegliere un metodo di pagamento", Alert.AlertType.ERROR);
            } else {
                cart.impostaMetodoPagamento(metodoPagamentoSelezionato);
                cart.effettuaPagamento();
                showAlert("Pagamento Effettuato", "Pagamento effettuato con successo", "Il pagamento è stato eseguito correttamente", Alert.AlertType.INFORMATION);
                cart.getProdotti().clear();
                listprod.getItems().clear();
                listqtcart.getItems().clear();
            }
        }
    }

    @FXML
    void BackPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Catalogo.fxml"));
            Parent root = loader.load();

            // Ottieni il controller associato al secondo file
            Catalogo secondoCatalogo = loader.getController();

            // Passa l'oggetto cart al secondo controller
            secondoCatalogo.setCarrello(cart);

            // Altre operazioni se necessario
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            // Visualizza la scena
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, String s, Alert.AlertType information) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

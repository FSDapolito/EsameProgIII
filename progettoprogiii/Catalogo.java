package com.example.progettoprogiii;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import static javafx.scene.input.KeyCode.O;

public class Catalogo {
    String url = "jdbc:mysql://localhost:3306/ricambi";
    String username = "root";
    String password = "Programmazione3";
    @FXML
    private ComboBox<String> Fornitori;
    @FXML
    private ComboBox<String> categoria;
    @FXML
    private AnchorPane ListaProdotti;

    @FXML
    private Button ricerca;
    @FXML
    private ListView<String> listaprod;
    @FXML
    private ListView<Integer> qt;
    @FXML
    private Button Add;
    @FXML
    private Button remove;
    public void setCarrello(Carrello cart) {
        this.cart = cart;
    }
    @FXML
    void NextPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Cart.fxml"));
            Parent root = loader.load();

            // Ottieni il controller associato al secondo file
            CarrelloController secondoController = loader.getController();

            // Passa l'oggetto cart al secondo controller
            secondoController.setCarrello(cart);

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


    Carrello cart = new Carrello();

    @FXML
    void addCarrello(MouseEvent event) {
        String NomeP = "";
        Double Costo = 0.0;
        String Token = "";



        ObservableList<Integer> qcarrello = FXCollections.observableArrayList();
        ObservableList<String> nomeprod = FXCollections.observableArrayList();
        qcarrello.addAll(qt.getItems());
        nomeprod.addAll(listaprod.getItems());
        Integer contQT=0;
        for (int i = 0; i < qcarrello.size(); i++) {
            Integer check = qcarrello.get(i);
            contQT=contQT+check;
            if (check > 0 && contQT<11) {
                NomeP = "";
                Costo = 0.0;
                Token = "";

                Prodotto prodotto = new Prodotto();
                NomeP = nomeprod.get(i);
                String[] Appoggio = NomeP.split("\\s+");

                // Estrai il nome e il prezzo dalle parti
                NomeP = Appoggio[0];
                Costo = Double.parseDouble(Appoggio[1]);

                prodotto.setNome(NomeP);
                prodotto.setCosto(Costo);

                prodotto.setScorta(check);
                cart.aggiungiProdotto(prodotto);
                //cart.setListViewQuantita(i, check);


            }else if(cart.isVuoto()== true){
                NomeP = "";
                Costo = 0.0;
                Token = "";

                Prodotto prodotto = new Prodotto();
                NomeP = nomeprod.get(i);
                String[] Appoggio = NomeP.split("\\s+");

                // Estrai il nome e il prezzo dalle parti
                NomeP = Appoggio[0];
                Costo = Double.parseDouble(Appoggio[1]);

                prodotto.setNome(NomeP);
                prodotto.setCosto(Costo);

                prodotto.setScorta(10);
                cart.aggiungiProdotto(prodotto);

                showAlert("Carrello Pieno", "Massimo 10 pezzi", "Massimo 10 pezzi", Alert.AlertType.INFORMATION);
            }else if (contQT>10) showAlert("Carrello Pieno", "Il carrello è pieno", "Massimo 10 pezzi", Alert.AlertType.INFORMATION);



        }

        Integer index = 0;
        for (Prodotto x : cart.getProdotti()
        ) {
            String linea = "";
            linea = x.getNome() + " " + String.valueOf(x.getCosto()) + " €";
            //cart.setListViewProdotti(index, linea);
            index++;

        }


    }
    @FXML
    void removeCarrello(MouseEvent event) {
        ObservableList<Integer> qcarrello = FXCollections.observableArrayList();
        ObservableList<String> nomeprod = FXCollections.observableArrayList();
        qcarrello.addAll(qt.getItems());
        nomeprod.addAll(listaprod.getItems());

        Integer contQT = 0;
        int i = 0;  // Aggiungi un indice per tenere traccia dell'elemento corrente
        while (i < qcarrello.size()) {
            Integer check = qcarrello.get(i);

            if (check > 0) {
                // Ottieni l'oggetto Prodotto dalla lista prodotti
                Prodotto prodotto = cart.getProdotti().get(i);

                // Chiamata al metodo rimuoviProdotto del tuo carrello
                cart.rimuoviProdotto(prodotto, check);

                // Rimuovi l'elemento dalla lista corrente
                qcarrello.remove(i);
                nomeprod.remove(i);
            } else {
                i++;  // Passa all'elemento successivo solo se la quantità è 0
            }
        }
    }


    private void showAlert(String title, String content, String s, Alert.AlertType information) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Popola la ComboBox dei fornitori
        populateComboBox(Fornitori, "fornitori");
        populateComboBox2(categoria, "categorie");

    }

    @FXML
    private void populateProdottiComboBox(MouseEvent event) {
        String query = "";
        String fornitore = Fornitori.getValue();
        String categorie = categoria.getValue();
        double param = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Utilizza una query parametrizzata per evitare SQL injection
            if (fornitore != null && categorie != null) {
                query = "SELECT prodotti.nome,prodotti.costo FROM prodotti JOIN fornitori ON prodotti.fornitore_id = fornitori.id JOIN categorie ON prodotti.categoria_id = categorie.id WHERE fornitori.nome = ? AND categorie.nome = ?;";
                param = 2;
            } else if (categorie == null) {
                query = "SELECT nome,costo FROM prodotti WHERE fornitore_id IN (SELECT id FROM fornitori WHERE nome = ?)";
                param = 1;
            } else if (fornitore == null) {
                query = "SELECT nome,costo FROM prodotti WHERE categoria_id IN (SELECT id FROM categorie WHERE nome = ?)";
                param = 1.1;
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                if (param == 2) {
                    preparedStatement.setString(1, fornitore);
                    preparedStatement.setString(2, categorie);
                } else if (param == 1) {
                    preparedStatement.setString(1, fornitore);

                } else if (param == 1.1) {
                    preparedStatement.setString(1, categorie);
                }


                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ObservableList<String> data = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        String Nome = "";
                        double Prezzo = 0.0;
                        Nome = resultSet.getString("nome");
                        Prezzo = resultSet.getDouble("costo");

                        data.add(Nome + "      " + String.valueOf(Prezzo) + " €");
                    }
                    listaprod.setItems(data);

                    ObservableList<Integer> quantita = FXCollections.observableArrayList();
                    //qt=new ListView<>();
                    for (int i = 0; i < data.size(); i++) {
                        quantita.add(0);
                    }
                    qt.setItems(quantita);

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

    private void populateComboBox(ComboBox<String> Fornitori, String tableName) {


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT nome FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                ObservableList<String> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    data.add(resultSet.getString("nome"));
                }
                Fornitori.setItems(data);

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Errore durante l'esecuzione della query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Errore durante la connessione al database.");
        }
    }

    private void populateComboBox2(ComboBox<String> categoria, String tableName) {


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT nome FROM " + tableName;
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                ObservableList<String> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    data.add(resultSet.getString("nome"));
                }
                categoria.setItems(data);

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
    void BackPage(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("ClientHomePage.fxml"));
            Parent root2 = (Parent)loader.load();
            Stage terzaStage = new Stage();
            terzaStage.setTitle("Admin Homepage");
            terzaStage.setScene(new Scene(root2));
            Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            currentStage.close();
            terzaStage.show();
        } catch (Exception var6) {
            System.out.println("Errore di Login");
        }

    }
    private final List<Command> commandHistory = new ArrayList<>();

    @FXML
    private void handleAddProduct(MouseEvent event) {
        Integer quantita = qt.getSelectionModel().getSelectedItem();
        int i = qt.getSelectionModel().getSelectedIndex();
        if (quantita != null) {
            Command addCommand = new AddProdottoCarr(this, quantita, i);
            addCommand.execute(quantita, i);
            commandHistory.add(addCommand);
        }
    }

    @FXML
    private void handleRemoveProduct(MouseEvent event) {
        Integer quantita = qt.getSelectionModel().getSelectedItem();
        int i = qt.getSelectionModel().getSelectedIndex();
        if (quantita != null && quantita >0) {
            Command removeCommand = new RemoveProdottoCarr(this, quantita, i);
            removeCommand.execute(quantita, i);
            commandHistory.add(removeCommand);
        }
    }

    public void aggiungiProdotto(Integer quantita, Integer i) {
        Integer j = 0;
        quantita = quantita + 1;

        ObservableList<Integer> qtt = FXCollections.observableArrayList();
        qtt.addAll(qt.getItems());
        qtt.set(i, quantita);
        qt.setItems(qtt);

        System.out.println("Prodotto aggiunto: " + String.valueOf(quantita));
    }

    public void rimuoviProdotto(Integer quantita, Integer i) {
        Integer j = 0;
        quantita = quantita - 1;

        ObservableList<Integer> qtt = FXCollections.observableArrayList();
        qtt.addAll(qt.getItems());
        qtt.set(i, quantita);
        qt.setItems(qtt);

        System.out.println("Prodotto rimosso: " + String.valueOf(quantita));
    }
}
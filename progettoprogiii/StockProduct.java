package com.example.progettoprogiii;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class StockProduct {
    @FXML
    private Button AspirapolvereButton;
    @FXML
    private Button FrigoButton;
    @FXML
    private ImageView Immagine;
    @FXML
    private Button LavastoviglieButton;
    @FXML
    private Button LavatriceButton;
    @FXML
    private ListView<String> tabellaNome;
    @FXML
    private ListView<Integer> tabellaNumero;
    @FXML
    private Text ProdottiButton;
    @FXML
    private Text ProdottiButton1;
    @FXML
    private ImageView arrow;

    public StockProduct() {
    }

    @FXML
    void InfoAspirapolvere(MouseEvent event) {
        String nomeImmagine = "Aspirapolvere.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.Immagine.setImage(image);
        this.caricaDatiCategoria("aspirapolvere");
    }

    @FXML
    void InfoFrigorifero(MouseEvent event) {
        String nomeImmagine = "Frigo.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.Immagine.setImage(image);
        this.caricaDatiCategoria("frigorifero");
    }

    @FXML
    void InfoLavastoviglie(MouseEvent event) {
        String nomeImmagine = "Lavastoviglie.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.Immagine.setImage(image);
        this.caricaDatiCategoria("lavastoviglie");
    }

    @FXML
    void InfoLavatrice(MouseEvent event) {
        String nomeImmagine = "Lavatrice.png";
        Image image = new Image(this.getClass().getResourceAsStream(nomeImmagine));
        this.Immagine.setImage(image);
        this.caricaDatiCategoria("lavatrice");
    }

    @FXML
    void backFile(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AdminHomepage.fxml"));
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

    private void caricaDatiCategoria(String categoria) {
        // Configura la connessione al database
        String url = "jdbc:mysql://localhost:3306/ricambi";
        String username = "root";
        String password = "Programmazione3";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT nome, scorta FROM prodotti WHERE categoria_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Imposta il parametro della categoria
                int categoriaId = ottieniIdCategoria(categoria); // Metodo da implementare
                preparedStatement.setInt(1, categoriaId);

                // Esegui la query
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Popola le liste con i dati risultanti dalla query
                    ObservableList<String> nomi = FXCollections.observableArrayList();
                    ObservableList<Integer> scorte = FXCollections.observableArrayList();

                    while (resultSet.next()) {
                        nomi.add(resultSet.getString("nome"));
                        scorte.add(resultSet.getInt("scorta"));
                    }

                    // Imposta i dati nelle ListView
                    tabellaNome.setItems(nomi);
                    tabellaNumero.setItems(scorte);

                    //controllo OBSERVER
                    for(int i = 0; i<nomi.size(); i++){
                        RicambioObservable ricambio = new RicambioObservable(nomi.get(i));
                        ComponenteVerificaScorta verificaScorta = new ComponenteVerificaScorta();
                        ricambio.addObserver(verificaScorta);
                        ricambio.updateScortaFromDatabase();
                    }


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
        if(categoriaNome.equals("Aspirapolvere") || categoriaNome.equals("aspirapolvere")){ return 1;}
        else if(categoriaNome.equals("Frigorifero")|| categoriaNome.equals("frigorifero")){ return 2;}
        else if (categoriaNome.equals("Lavatrice") ||categoriaNome.equals("lavatrice") ) {return 3;}
        else if (categoriaNome.equals("Lavastoviglie") ||categoriaNome.equals("lavastoviglie") ) {return 4;}
        return -1;
    }

}
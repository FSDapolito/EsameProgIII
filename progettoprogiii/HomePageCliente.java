package com.example.progettoprogiii;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomePageCliente {

    @FXML
    private ImageView Catalogo;

    @FXML
    private ImageView Preventivo;

    @FXML
    private ImageView backArrow;

    @FXML
    void Preventivo(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Preventivo.fxml"));
            Parent BackRoot = (Parent) loader.load();
            Stage BackStage = new Stage();
            BackStage.setTitle("Preventivo");
            BackStage.setScene(new Scene(BackRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            BackStage.show();
        } catch (Exception e) {
            System.out.println("Errore di apertura pagina preventivo");
        }
    }

    @FXML
    void BackPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
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
    @FXML
    void NextPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Catalogo.fxml"));
            Parent BackRoot = (Parent) loader.load();
            Stage BackStage = new Stage();
            BackStage.setTitle("Catalogo");
            BackStage.setScene(new Scene(BackRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            BackStage.show();
        }
        catch (Exception e)
        {
            System.out.println("Errore di caricamento");
        }
    }

}

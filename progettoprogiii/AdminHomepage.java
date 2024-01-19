package com.example.progettoprogiii;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminHomepage {

    @FXML
    private ImageView AddButton;

    @FXML
    void AddNewProduct(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("NewProduct.fxml"));
            Parent BackRoot = (Parent) loader.load();
            Stage BackStage = new Stage();
            BackStage.setTitle("Add New Product");
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
    void ShowStockProduct(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StockProduct.fxml"));
            Parent BackRoot = (Parent) loader.load();
            Stage BackStage = new Stage();
            BackStage.setTitle("Stock Product");
            BackStage.setScene(new Scene(BackRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            BackStage.show();
        }
        catch (Exception e)
        {
            System.out.println("Errore di caricamento StockProduct.fxml");
        }
    }


    @FXML
    void PercentualiVendite(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SalesPercentages.fxml"));
            Parent BackRoot = (Parent) loader.load();
            Stage BackStage = new Stage();
            BackStage.setTitle("Sales Percentages");
            BackStage.setScene(new Scene(BackRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            BackStage.show();
        }
        catch (Exception e)
        {
            System.out.println("Errore di caricamento SalesPercentages.fxml");
        }
    }
    @FXML
    void backPage(MouseEvent event) {
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


}

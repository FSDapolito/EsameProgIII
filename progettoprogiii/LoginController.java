package com.example.progettoprogiii;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button AdminBT;

    @FXML
    private Button UserBT;

    @FXML
    void AdminButtonLogin(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
            Parent adminRoot = (Parent) loader.load();
            Stage AdminStage = new Stage();
            AdminStage.setTitle("Admin Login");
            AdminStage.setScene(new Scene(adminRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            AdminStage.show();
        }
        catch (Exception e)
        {
            System.out.println("Errore di Login");
        }
    }

    @FXML
    void UserButtonLogin(MouseEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLogin.fxml"));
            Parent adminRoot = (Parent) loader.load();
            Stage AdminStage = new Stage();
            AdminStage.setTitle("User Login");
            AdminStage.setScene(new Scene(adminRoot));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            AdminStage.show();
        }
        catch (Exception e)
        {
            System.out.println("Errore di Login");
        }
    }


}

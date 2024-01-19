package com.example.progettoprogiii;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public final class Login {
    private static Login instance;

    private Login() {
    }

    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
        }
        return instance;
    }

    public void CheckLogin(String username, String password, MouseEvent event) {
        if ((username.equalsIgnoreCase("Admin") || username.equalsIgnoreCase("admin")) &&
                (password.equals("Admin") || password.equals("admin"))) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminHomepage.fxml"));
                Parent root = fxmlLoader.load();
                Stage clientStage = new Stage();
                clientStage.setTitle("Admin Homepage");
                clientStage.setScene(new Scene(root));

                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.close();
                clientStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        else if ((username.equalsIgnoreCase("User") || username.equalsIgnoreCase("user")) &&
                (password.equals("User") || password.equals("user"))) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ClientHomePage.fxml"));
                Parent root = fxmlLoader.load();
                Stage clientStage = new Stage();
                clientStage.setTitle("Client Homepage");
                clientStage.setScene(new Scene(root));

                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.close();
                clientStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            // Credenziali errate, mostra avviso
            showAlert("ERRORE", "Credenziali errate, verifica username e password e riprova.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

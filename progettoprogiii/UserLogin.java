package com.example.progettoprogiii;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UserLogin {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    void handleLoginButtonAction(MouseEvent event) {
        Login user = Login.getInstance();
        String username = usernameField.getText();
        String password = passwordField.getText();
        user.CheckLogin(username,password,event);
    }

    @FXML
    void backPage(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent BackRoot = (Parent) loader.load();
            Stage BackStage = new Stage();
            BackStage.setTitle("Login Page");
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

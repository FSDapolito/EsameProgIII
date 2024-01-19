package com.example.progettoprogiii;

import javafx.scene.control.Alert;

public class ComponenteVerificaScorta implements Observer{
    @Override
    public void update(String nomeProdotto, int nuovaScorta) {
        if(nuovaScorta<sogliaSottoScorta){
            //IMPLEMENTARE LA LOGICA PER IL PROCESSO DI RIORDINO
            showAlert("Riordino necessario: "+nomeProdotto, "Il prodotto ha poche scorte");
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private static final int sogliaSottoScorta=10;
}
